package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.SospechosoDao;
import org.proyecto.dto.Sospechoso;

import java.sql.*;
import java.util.ArrayList;

public class SospechosoDaoMySql extends SospechosoDao {
    private static final String INSERT_QUERY = "INSERT INTO sospechoso (ciSospechoso, nombre, apellidoPaterno, apellidoMaterno) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE sospechoso SET ciSospechoso = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ? WHERE idSospechoso = ?";
    private static final String DELETE_QUERY = "DELETE FROM sospechoso WHERE idSospechoso = ?";
    private static final String SELECT_QUERY = "SELECT * FROM sospechoso WHERE idSospechoso = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM sospechoso";

    @Override
    public int insert(Sospechoso obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, obj.getCiSospechoso());
            stmt.setString(2, obj.getNombre());
            stmt.setString(3, obj.getApellidoPaterno());
            stmt.setString(4, obj.getApellidoMaterno());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar el sospechoso");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar el sospechoso en la base de datos");
        }finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(Sospechoso obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
            stmt.setInt(5, obj.getId());
            stmt.setInt(1, obj.getCiSospechoso());
            stmt.setString(2, obj.getNombre());
            stmt.setString(3, obj.getApellidoPaterno());
            stmt.setString(4, obj.getApellidoMaterno());
            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar el sospechoso en la base de datos");
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
    public Sospechoso get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createSospechosoFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró el departamento con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<Sospechoso> getList() throws Exception {
        ArrayList<Sospechoso> sospechosos = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                sospechosos.add(createSospechosoFromResultSet(resultSet));
            }
            return sospechosos;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private Sospechoso createSospechosoFromResultSet(ResultSet resultSet) throws SQLException {
        Sospechoso sospechoso = new Sospechoso();
        sospechoso.setId(resultSet.getInt("idSospechoso"));
        sospechoso.setCiSospechoso(resultSet.getInt("ciSospechoso"));
        sospechoso.setNombre(resultSet.getString("nombre"));
        sospechoso.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
        sospechoso.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
        return sospechoso;
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