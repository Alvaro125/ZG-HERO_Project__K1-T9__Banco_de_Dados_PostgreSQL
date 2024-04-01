package config

import org.example.config.Database
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import java.sql.Connection
import java.sql.SQLException

import static org.junit.jupiter.api.Assertions.*

class DatabaseTest {
    static Connection conn

    @BeforeAll
    static void setUp() throws SQLException {
        conn = Database.getConnection()
    }

    @AfterAll
    static void tearDown() throws SQLException {
        Database.closeConnection(conn)
    }

    @Test
    void testConnectionNotNull() {
        //Given:
        //When:
        //Then:
        assertNotNull(conn)
    }

    @Test
    void testConnectionIsClosed() {
        //Given:
        //When:
        //Then:
        assertFalse(conn.isClosed())
    }

    @Test
    void testConnectionClosed() {
        //Given:
        //When:
        Database.closeConnection(conn)
        //Then:
        assertTrue(conn.isClosed())
    }

    @Test
    void testConnectionIsValid() {
        //Given:
        //When:
        //Then:
        assertTrue(conn.isValid(1))
    }
}
