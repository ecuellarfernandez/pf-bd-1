package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.CrimenDao;
import org.proyecto.dto.Crimen;

import java.sql.*;
import java.util.ArrayList;

public class CrimenDaoMySql extends CrimenDao {
    private static final String INSERT_QUERY = "INSERT INTO crimen (descripcion, fecha, hora, idCategoria) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE crimen SET descripcion = ?, fecha = ?, hora = ?, idCategoria = ? WHERE idCrimen = ?";
    private static final String DELETE_QUERY = "DELETE FROM crimen WHERE idCrimen = ?";
    private static final String SELECT_QUERY = "SELECT * FROM crimen WHERE idCrimen = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM crimen";

    @Override
    public int insert(Crimen obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, obj.getDescripcion());
            stmt.setDate(2, obj.getFecha());
            stmt.setTime(3, obj.getHora());
            stmt.setInt(4, obj.getIdCategoria());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar crimen");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la crimen en la base de datos");
        } finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(Crimen obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);

            stmt.setInt(5, obj.getId());
            stmt.setString(1, obj.getDescripcion());
            stmt.setDate(2, obj.getFecha());
            stmt.setTime(3, obj.getHora());
            stmt.setInt(4, obj.getIdCategoria());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar crimen en la base de datos");
        }
    }

    @Override
    public void delete(int id) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public Crimen get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createCrimenFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró Crimen con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<Crimen> getList() throws Exception {
        ArrayList<Crimen> pruebas = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pruebas.add(createCrimenFromResultSet(resultSet));
            }
            return pruebas;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private Crimen createCrimenFromResultSet(ResultSet resultSet) throws SQLException {
        Crimen crimen = new Crimen();
        crimen.setId(resultSet.getInt("idCrimen"));
        crimen.setDescripcion(resultSet.getString("descripcion"));
        crimen.setFecha(resultSet.getDate("fecha"));
        crimen.setHora(resultSet.getTime("hora"));
        crimen.setIdCategoria(resultSet.getInt("idCategoria"));
        return crimen;
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
