package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.OrdenDeBusquedaDao;
import org.proyecto.dto.OrdenDeBusqueda;

import java.sql.*;
import java.util.ArrayList;

public class OrdenDeBusquedaDaoMySql extends OrdenDeBusquedaDao {
    private static final String INSERT_QUERY = "INSERT INTO orden_de_busqueda (estado, idAgente, idSospechoso, idInvestigacion) VALUES (?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE orden_de_busqueda SET estado = ?, idAgente = ?, idSospechoso = ?, idInvestigacion = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM orden_de_busqueda WHERE idOrden = ?";
    private static final String SELECT_QUERY = "SELECT * FROM orden_de_busqueda WHERE idOrden = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM orden_de_busqueda";

    @Override
    public int insert(OrdenDeBusqueda obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setBoolean(1, obj.getEstado());
            stmt.setInt(2, obj.getIdAgente());
            stmt.setInt(3, obj.getIdSospechoso());
            stmt.setInt(4, obj.getIdInvestigacion());


            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar la Orden de Busqueda");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la Orden en la base de datos");
        }finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(OrdenDeBusqueda obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
            stmt.setInt(2, obj.getId());
            stmt.setBoolean(1, obj.getEstado());
            stmt.setInt(2, obj.getIdAgente());
            stmt.setInt(3, obj.getIdSospechoso());
            stmt.setInt(4, obj.getIdInvestigacion());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la orden en la base de datos");
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
    public OrdenDeBusqueda get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createOrdenFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró la categoria con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<OrdenDeBusqueda> getList() throws Exception {
        ArrayList<OrdenDeBusqueda> ordenes = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ordenes.add(createOrdenFromResultSet(resultSet));
            }
            return ordenes;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private OrdenDeBusqueda createOrdenFromResultSet(ResultSet resultSet) throws SQLException {
        OrdenDeBusqueda orden = new OrdenDeBusqueda();
        orden.setId(resultSet.getInt("idOrden"));
        orden.setEstado(resultSet.getBoolean("estado"));
        orden.setIdAgente(resultSet.getInt("idAgente"));
        orden.setIdSospechoso(resultSet.getInt("idSospechoso"));
        orden.setIdInvestigacion(resultSet.getInt("idInvestigacion"));
        return orden;
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