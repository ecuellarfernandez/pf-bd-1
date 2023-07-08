package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.EquipoDao;
import org.proyecto.dto.Equipo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EquipoDaoMySql extends EquipoDao {
    private static final String INSERT_QUERY = "INSERT INTO equipo (nombre) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE equipo SET nombre = ? WHERE idEquipo = ?";
    private static final String DELETE_QUERY = "DELETE FROM equipo WHERE idEquipo = ?";
    private static final String SELECT_QUERY = "SELECT * FROM equipo WHERE idEquipo = ?";
    @Override
    public int insert(Equipo equipo) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        int equipoId = 0;
        try {
            statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, equipo.getNombre());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                equipoId = resultSet.getInt(1);
            }
            if (equipoId == 0) {
                throw new Exception("No se pudo insertar el equipo");
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return equipoId;
    }

    private static final String SELECT_ALL_QUERY = "SELECT * FROM equipo";

    @Override
    public void update(Equipo equipo) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, equipo.getNombre());
            statement.setInt(2, equipo.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println(equipo.getId() + " " + equipo.getNombre());
                throw new Exception("No se pudo actualizar el equipo");
            }
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public void delete(int idEquipo) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, idEquipo);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("No se pudo eliminar el equipo");
            }
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public Equipo get(int idEquipo) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Equipo equipo = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, idEquipo);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                equipo = createEquipoFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró el equipo");
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return equipo;
    }

    @Override
    public ArrayList<Equipo> getList() throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Equipo> equipos = new ArrayList<>();
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Equipo equipo = createEquipoFromResultSet(resultSet);
                equipos.add(equipo);
            }
        } finally {
            closeResources(statement, resultSet);
        }
        return equipos;
    }

    private Equipo createEquipoFromResultSet(ResultSet resultSet) throws Exception {
        Equipo equipo = new Equipo();
        equipo.setId(resultSet.getInt("idEquipo"));
        equipo.setNombre(resultSet.getString("nombre"));
        return equipo;
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
