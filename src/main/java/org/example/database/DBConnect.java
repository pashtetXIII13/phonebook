package org.example.database;

import java.sql.Connection;

/**
 * @author pashtet
 */
public interface DBConnect {
    Connection getConnect();

    Connection getConnect(String host, String dbname, String username, String password);
}
