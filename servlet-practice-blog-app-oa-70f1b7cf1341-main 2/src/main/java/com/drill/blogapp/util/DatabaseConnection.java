package com.drill.blogapp.util;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://mysql-tcs.drillinsight.com:3306/Melody_Practice";
    private static final String USERNAME = "melody";
    private static final String PASSWORD = "MoonDrop7735";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("MySQL JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e){
            log.error("MySQL JDBC Driver not found", e);
            throw new RuntimeException("Failed to load MySQL JDBC Driver", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USERNAME, DatabaseConnection.PASSWORD);
            log.info("Database connection established successfully");
            return conn;
        } catch (SQLException e) {
            log.error("Failed to establish database connection", e);
            throw e;
        }
    }
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                log.info("Database connection closed successfully");
            } catch (SQLException e) {
                log.error("Error closing database connection", e);
            }
        }
    }
}


