package org.example.utils;

import org.example.database.ConnectionPool;
import org.example.database.psql.PSQLDBConnect;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * @author pashtet
 */
public class ConnectionPoolTest {

    private static final Connection[] connections = new Connection[10];
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void setUp() {
        connectionPool = ConnectionPool.create(new PSQLDBConnect());
        for (int i = 0; i < 10; i++) {
            connections[i] = connectionPool.getConnection();
        }
    }

    @AfterClass
    public static void close() throws SQLException {
        connectionPool.close();
        assertFalse(connections[0].isValid(1));
    }

    @Test
    public void testGetConnect() throws SQLException {
        for (int i = 0; i < 10; i++) {
            assertTrue(connections[i].isValid(1));
        }
    }

    @Test
    public void releaseConnection() {
        //   assertNull(connectionPool.getConnection());
        connectionPool.releaseConnection(connections[0]);
        assertNotNull(connectionPool.getConnection());
    }
}