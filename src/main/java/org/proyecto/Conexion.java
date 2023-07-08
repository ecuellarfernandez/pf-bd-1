package org.proyecto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private String host = "127.0.0.1";
    private String port = "3306";
    private String database = "fbi";
    private String user = "root";
    private String pass = "root";
    private Connection objConnection;

    private static Conexion instance;

    private Conexion() {
        // Constructor privado para evitar instanciación externa
    }

    public static synchronized Conexion getOrCreate() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public Connection conectarMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            objConnection = DriverManager.getConnection(connectionString, user, pass);
            System.out.println("Conexion MySQL - Java EXITOSA");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return objConnection;
    }

    public void ejecutarSQL(String sql) {
        try (Connection con = conectarMySQL()) {
            Statement consulta = con.createStatement();
            consulta.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //consultas select
    public ResultSet ejecutar(String sql) {
        ResultSet resultSet = null;
        try {
            Connection con = conectarMySQL();
            Statement consulta = con.createStatement();
            resultSet = consulta.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
    //update delete
    public int ejecutarSimple(String query) {
        try {
            Statement stmt = objConnection.createStatement();
            int nb = stmt.executeUpdate(query);
            return nb;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //insert
    public int ejecutarInsert(String query) {
        try {
            Statement stmt = objConnection.createStatement();
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void desconectar() {
        try {
            if (estaConectado()) {
                objConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean estaConectado() {
        if (objConnection == null) {
            return false;
        }
        try {
            if (objConnection.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
            objConnection = null;
            return false;
        }
        return true;
    }
}
