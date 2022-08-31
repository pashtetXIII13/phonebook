package org.example.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.ConnectionPoolEmpty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pashtet
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int INITIAL_POOL_SIZE = 10;
    private static final List<Connection> CONNECTIONS = new ArrayList<>(INITIAL_POOL_SIZE);
    private final List<Connection> usedConnections = new ArrayList<>(INITIAL_POOL_SIZE);

    public static ConnectionPool create(final DBConnect dbConnect) {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            CONNECTIONS.add(dbConnect.getConnect());
        }
        return new ConnectionPool();
    }

    public Connection getConnection() {
        try {
            if (CONNECTIONS.isEmpty()) throw new ConnectionPoolEmpty("Connection Pool Empty");
        } catch (ConnectionPoolEmpty e) {
            logger.error(e.getMessage());
            return null;
        }
        Connection connection = CONNECTIONS
                .remove(CONNECTIONS.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public void releaseConnection(final Connection connection) {
        CONNECTIONS.add(connection);
        usedConnections.remove(connection);
    }

    public void close() {
        usedConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        });
        CONNECTIONS.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        });
        usedConnections.clear();
        CONNECTIONS.clear();
    }
}
