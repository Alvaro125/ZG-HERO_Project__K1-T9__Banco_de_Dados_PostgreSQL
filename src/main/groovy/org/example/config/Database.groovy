package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {
    static final String URL = "jdbc:postgresql://localhost:15432/linketinder"
    static final String USER = "postgres"
    static final String PASSWORD = "postgres"
    public static Connection conn;

    static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver())
        conn = DriverManager.getConnection(URL, USER, PASSWORD)
    }

    static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close()
        }
    }
}
