package org.proyecto.dao.impl;

import org.proyecto.Conexion;
import org.proyecto.dao.DepartamentoDao;
import org.proyecto.dto.Departamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartamentoDaoMySql extends DepartamentoDao {
    private static final String INSERT_QUERY = "INSERT INTO departamento (nombre, descripcion, idAdministrador) VALUES (?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE departamento SET nombre = ?, descripcion = ?, idAdministrador = ? WHERE idDepartamento = ?";
    private static final String DELETE_QUERY = "DELETE FROM departamento WHERE idDepartamento = ?";
    private static final String SELECT_QUERY = "SELECT * FROM departamento WHERE idDepartamento = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM departamento";
    private static final String UPDATE_AGENTE_QUERY = "UPDATE agente SET idDepartamento = ? WHERE idAgente = ?";
    @Override
    public int insert(Departamento departamento) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(INSERT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, departamento.getNombre());
            statement.setString(2, departamento.getDescripcion());
            int idAdministador = departamento.getIdAdministador();
            statement.setInt(3, idAdministador);
//            if(idAdministador > 0){
//                statement.setInt(3, idAdministador);
//                asignarDepartamentoAgente(departamento.getId(), idAdministador);
//            }else{
//                statement.setNull(3, java.sql.Types.INTEGER);
//            }

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new Exception("No se pudo obtener el ID generado para el departamento.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

//    public void asignarDepartamentoAgente(int idDepartamento, int idAgente){
//        PreparedStatement statement = null;
//        Conexion objConexion = Conexion.getOrCreate();
//        Connection conn = objConexion.conectarMySQL();
//        try{
//            statement = conn.prepareStatement(UPDATE_AGENTE_QUERY);
//            statement.setInt(1, idDepartamento);
//            statement.setInt(2, idAgente);
//            System.out.println(statement);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }finally {
//            closeResources(statement, null);
//        }
//    }

    @Override
    public void update(Departamento departamento) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, departamento.getNombre());
            statement.setString(2, departamento.getDescripcion());
            int idAdministrador = departamento.getIdAdministador();
            statement.setInt(3, idAdministrador);
//            if(idAdministrador > 0){
//                statement.setInt(3, departamento.getIdAdministador());
//                asignarDepartamentoAgente(departamento.getId(), idAdministrador);
//            }else {
//                statement.setNull(3, java.sql.Types.INTEGER);
//            }

            statement.setInt(4, departamento.getId());
            statement.executeUpdate();
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public void delete(int idDepartamento) throws Exception {
        PreparedStatement statement = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, idDepartamento);
            statement.executeUpdate();
        } finally {
            closeResources(statement, null);
        }
    }

    @Override
    public Departamento get(int idDepartamento) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_QUERY);
            statement.setInt(1, idDepartamento);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createDepartamentoFromResultSet(resultSet);
            } else {
                throw new Exception("No se encontró el departamento con el ID proporcionado.");
            }
        } finally {
            closeResources(statement, resultSet);
        }
    }

    @Override
    public ArrayList<Departamento> getList() throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Conexion objConexion = Conexion.getOrCreate();
        Connection conn = objConexion.conectarMySQL();
        try {
            statement = conn.prepareStatement(SELECT_ALL_QUERY);
            resultSet = statement.executeQuery();
            ArrayList<Departamento> departamentos = new ArrayList<>();
            while (resultSet.next()) {
                Departamento departamento = createDepartamentoFromResultSet(resultSet);
                departamentos.add(departamento);
            }
            return departamentos;
        } finally {
            closeResources(statement, resultSet);
        }
    }

    private Departamento createDepartamentoFromResultSet(ResultSet resultSet) throws Exception {
        Departamento departamento = new Departamento();
        departamento.setId(resultSet.getInt("idDepartamento"));
        departamento.setNombre(resultSet.getString("nombre"));
        departamento.setDescripcion(resultSet.getString("descripcion"));
        departamento.setIdAdministador(resultSet.getInt("idAdministrador"));
        return departamento;
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
