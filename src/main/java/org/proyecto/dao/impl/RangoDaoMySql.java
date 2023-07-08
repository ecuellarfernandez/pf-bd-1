package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.RangoDao;
import org.proyecto.dto.Rango;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class RangoDaoMySql extends RangoDao {
    private static final String INSERT_QUERY = "INSERT INTO rango (nombre) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE rango SET nombre = ? WHERE idRango = ?";
    private static final String DELETE_QUERY = "DELETE FROM rango WHERE idRango = ?";
    private static final String SELECT_QUERY = "SELECT * FROM rango WHERE idRango = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM rango";

    @Override
    public int insert(Rango rango) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = 0;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, rango.getNombre());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return id;
    }

    @Override
    public void update(Rango rango) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, rango.getNombre());
            statement.setInt(2, rango.getId());
            statement.executeUpdate();
        } finally {
            closeResources(statement, null);
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
    public Rango get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Rango rango = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rango = createRangoFromResultSet(resultSet);
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return rango;
    }

    @Override
    public ArrayList<Rango> getList() throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Rango> rangos = new ArrayList<>();
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Rango rango = createRangoFromResultSet(resultSet);
                rangos.add(rango);
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return rangos;
    }

    private Rango createRangoFromResultSet(ResultSet resultSet) throws Exception {
        Rango rango = new Rango();
        rango.setId(resultSet.getInt("idRango"));
        rango.setNombre(resultSet.getString("nombre"));
        return rango;
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