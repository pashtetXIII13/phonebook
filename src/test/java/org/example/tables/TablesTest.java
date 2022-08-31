package org.example.tables;

import org.example.constants.Tables;
import org.example.database.ConnectionPool;
import org.example.database.DBStatement;
import org.example.database.psql.PSQLDBConnect;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

/**
 * @author pashtet
 */
public class TablesTest {

    @Test
    public void testCreateTable() {
        ConnectionPool connectionPool = ConnectionPool.create(new PSQLDBConnect());
        Connection connection = connectionPool.getConnection();
        Statement statement = DBStatement.getStatement(connection);
        assertTrue(Tables.create(statement));
        connectionPool.releaseConnection(connection);
    }

}