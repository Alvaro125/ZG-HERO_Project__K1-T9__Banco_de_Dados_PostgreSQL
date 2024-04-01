package utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DBTest {
    static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    static final String USER = "sa";
    static final String PASSWORD = "";

    public static Connection conn;

    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }

    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
