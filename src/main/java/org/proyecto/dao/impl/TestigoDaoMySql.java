package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.TestigoDao;
import org.proyecto.dto.Testigo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TestigoDaoMySql extends TestigoDao {
    private static final String INSERT_QUERY = "INSERT INTO testigo (ciTestigo, nombre, apellidoPaterno, apellidoMaterno) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE testigo SET ciTestigo = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ? WHERE idTestigo = ?";
    private static final String DELETE_QUERY = "DELETE FROM testigo WHERE idTestigo = ?";
    private static final String SELECT_QUERY = "SELECT * FROM testigo WHERE idTestigo = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM testigo";

    @Override
    public int insert(Testigo testigo) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        int testigoId = 0;
        try {
            statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, testigo.getCiTestigo());
            statement.setString(2, testigo.getNombre());
            statement.setString(3, testigo.getApellidoPaterno());
            statement.setString(4, testigo.getApellidoMaterno());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                testigoId = resultSet.getInt(1);
            }
            if (testigoId == 0) {
                throw new Exception("No se pudo insertar el testigo");
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return testigoId;
    }

    @Override
    public void update(Testigo testigo) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setInt(1, testigo.getCiTestigo());
            statement.setString(2, testigo.getNombre());
            statement.setString(3, testigo.getApellidoPaterno());
            statement.setString(4, testigo.getApellidoMaterno());
            statement.setInt(5, testigo.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("No se pudo actualizar el testigo");
            }
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public void delete(int idTestigo) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, idTestigo);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("No se pudo eliminar el testigo");
            }
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public Testigo get(int idTestigo) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        Testigo testigo = null;
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, idTestigo);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                testigo = new Testigo();
                testigo.setId(resultSet.getInt("idTestigo"));
                testigo.setCiTestigo(resultSet.getInt("ciTestigo"));
                testigo.setNombre(resultSet.getString("nombre"));
                testigo.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
                testigo.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return testigo;
    }

    @Override
    public ArrayList<Testigo> getList() throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        ArrayList<Testigo> testigos = new ArrayList<>();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Testigo testigo = createTestigo(resultSet);
                testigos.add(testigo);
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return testigos;
    }

    public Testigo createTestigo(ResultSet resultSet) throws Exception {
        Testigo testigo = new Testigo();
        testigo.setId(resultSet.getInt("idTestigo"));
        testigo.setCiTestigo(resultSet.getInt("ciTestigo"));
        testigo.setNombre(resultSet.getString("nombre"));
        testigo.setApellidoPaterno(resultSet.getString("apellidoPaterno"));
        testigo.setApellidoMaterno(resultSet.getString("apellidoMaterno"));
        return testigo;
    }

    private void closeResources(PreparedStatement statement, ResultSet resultSet) throws Exception {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
    }
}