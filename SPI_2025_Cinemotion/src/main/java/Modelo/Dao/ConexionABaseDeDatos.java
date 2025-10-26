package Modelo.Dao;

import java.sql.*;

public class ConexionABaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/bdd_spi_2025";
    private static final String USER = "root";
    private static final String PASSWORD = "Pass89#1_.+&kEy*";

    private ConexionABaseDeDatos() {}

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
