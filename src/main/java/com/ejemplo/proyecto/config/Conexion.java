package com.ejemplo.proyecto.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PRUEBATECNICA;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() {
        try {
            // Cargar driver de SQL Server (opcional en JDBC moderno)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de SQL Server");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return null;
    }
}
