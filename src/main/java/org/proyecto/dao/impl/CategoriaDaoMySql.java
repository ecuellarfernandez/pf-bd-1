package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.CategoriaCrimenDao;
import org.proyecto.dto.CategoriaCrimen;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaDaoMySql extends CategoriaCrimenDao {
    private static final String INSERT_QUERY = "INSERT INTO categoria_crimen (nombre) VALUES (?)";
    private static final String UPDATE_QUERY = "UPDATE categoria_crimen SET nombre = ? WHERE idCategoria = ?";
    private static final String DELETE_QUERY = "DELETE FROM categoria_crimen WHERE idCategoria = ?";
    private static final String SELECT_QUERY = "SELECT * FROM categoria_crimen WHERE idCategoria = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM categoria_crimen";

    @Override
    public int insert(CategoriaCrimen obj) throws Exception {
        int id = 0;
        PreparedStatement stmt = null;
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            stmt = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, obj.getNombre());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el id luego de insertar la categoria");
            }

            if (id == 0) {
                throw new Exception("El registro no pudo ser insertado");
            }

            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al insertar la categoria en la base de datos");
        }finally {
            closeResources(stmt, null);
        }

        return id;
    }

    @Override
    public void update(CategoriaCrimen obj) throws Exception {
        try {
            Conexion objConexion = Conexion.getOrCreate();
            Connection conn = objConexion.conectarMySQL();
            PreparedStatement stmt = conn.prepareStatement(UPDATE_QUERY);
            stmt.setInt(2, obj.getId());
            stmt.setString(1, obj.getNombre());

            stmt.executeUpdate();
            stmt.close();
            objConexion.desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar la categoria en la base de datos");
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
    public CategoriaCrimen get(int id) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createCategoriaFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró la categoria con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<CategoriaCrimen> getList() throws Exception {
        ArrayList<CategoriaCrimen> categorias = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categorias.add(createCategoriaFromResultSet(resultSet));
            }
            return categorias;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private CategoriaCrimen createCategoriaFromResultSet(ResultSet resultSet) throws SQLException {
        CategoriaCrimen categoriaCrimen = new CategoriaCrimen();
        categoriaCrimen.setId(resultSet.getInt("idCategoria"));
        categoriaCrimen.setNombre(resultSet.getString("nombre"));
        return categoriaCrimen;
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
