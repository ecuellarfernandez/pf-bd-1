package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.InvestigacionTieneTestigoDao;
import org.proyecto.dto.InvestigacionTieneTestigo;

import java.sql.*;
import java.util.ArrayList;

public class InvestigacionTieneTestigoDaoMySql extends InvestigacionTieneTestigoDao {

    private static final String INSERT_QUERY = "INSERT INTO investigacion_tiene_testigo (idInvestigacion, idTestigo, declaracion, fecha) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE investigacion_tiene_testigo SET idInvestigacion = ?, idTestigo = ?, declaracion = ?, fecha = ? WHERE idRelacion = ?";
    private static final String DELETE_QUERY = "DELETE FROM investigacion_tiene_testigo WHERE idRelacion = ?";
    private static final String SELECT_QUERY = "SELECT * FROM investigacion_tiene_testigo WHERE idRelacion = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM investigacion_tiene_testigo";

    @Override
    public int insert(InvestigacionTieneTestigo obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, obj.getIdInvestigacion());
            stmt.setInt(2, obj.getIdTestigo());
            stmt.setString(3, obj.getDeclaracion());
            stmt.setDate(4, obj.getFecha());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar la InvestigacionTieneTestigo");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la InvestigacionTieneTestigo en la base de datos");
        }finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(InvestigacionTieneTestigo obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
            stmt.setInt(5, obj.getId());
            stmt.setInt(1, obj.getIdInvestigacion());
            stmt.setInt(2, obj.getIdTestigo());
            stmt.setString(3, obj.getDeclaracion());
            stmt.setDate(4, obj.getFecha());
            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la orden en la InvestigacionTieneTestigo");
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
    public InvestigacionTieneTestigo get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createInvestigacionTieneTestigoFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró la categoria con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<InvestigacionTieneTestigo> getList() throws Exception {
        ArrayList<InvestigacionTieneTestigo> ordenes = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ordenes.add(createInvestigacionTieneTestigoFromResultSet(resultSet));
            }
            return ordenes;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private InvestigacionTieneTestigo createInvestigacionTieneTestigoFromResultSet(ResultSet resultSet) throws SQLException {
        InvestigacionTieneTestigo investigacionTieneTestigo = new InvestigacionTieneTestigo();
        investigacionTieneTestigo.setId(resultSet.getInt("idRelacion"));
        investigacionTieneTestigo.setIdInvestigacion(resultSet.getInt("idInvestigacion"));
        investigacionTieneTestigo.setIdTestigo(resultSet.getInt("idTestigo"));
        investigacionTieneTestigo.setDeclaracion(resultSet.getString("declaracion"));
        investigacionTieneTestigo.setFecha(resultSet.getDate("fecha"));
        return investigacionTieneTestigo;
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
