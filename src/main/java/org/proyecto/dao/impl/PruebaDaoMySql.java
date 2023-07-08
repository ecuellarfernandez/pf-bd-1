package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.PruebaDao;
import org.proyecto.dto.Prueba;

import java.sql.*;
import java.util.ArrayList;

public class PruebaDaoMySql extends PruebaDao {

    private static final String INSERT_QUERY = "INSERT INTO prueba (descripcion, idCrimen) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE prueba SET descripcion = ?, idCrimen = ? WHERE idPrueba = ?";
    private static final String DELETE_QUERY = "DELETE FROM prueba WHERE idPrueba = ?";
    private static final String SELECT_QUERY = "SELECT * FROM prueba WHERE idPrueba = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM prueba";

    @Override
    public int insert(Prueba obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, obj.getDescripcion());
            stmt.setInt(2, obj.getIdCrimen());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar la prueba");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la prueba en la base de datos");
        }finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(Prueba obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);

            stmt.setInt(3, obj.getId());
            stmt.setString(1, obj.getDescripcion());
            stmt.setInt(2, obj.getIdCrimen());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la prueba en la base de datos");
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
    public Prueba get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createPruebaFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró la categoria con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<Prueba> getList() throws Exception {
        ArrayList<Prueba> pruebas = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                pruebas.add(createPruebaFromResultSet(resultSet));
            }
            return pruebas;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private Prueba createPruebaFromResultSet(ResultSet resultSet) throws SQLException {
        Prueba prueba = new Prueba();
        prueba.setId(resultSet.getInt("idPrueba"));
        prueba.setDescripcion(resultSet.getString("descripcion"));
        prueba.setIdCrimen(resultSet.getInt("idCrimen"));
        return prueba;
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