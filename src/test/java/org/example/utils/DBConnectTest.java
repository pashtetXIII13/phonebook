package org.example.utils;

import org.example.database.psql.PSQLDBConnect;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * @author pashtet
 */
public class DBConnectTest {
    private final Connection connection;
    PSQLDBConnect psql = new PSQLDBConnect();

    public DBConnectTest() {
        connection = psql.getConnect();
    }

    @Test
    public void testGetConnect() {
        try (connection) {
            assertNotNull(connection);
        } catch (SQLException e) {
            System.out.println("ErrorDB: Invalid password, username or host");
            e.printStackTrace();
        }
    }
}