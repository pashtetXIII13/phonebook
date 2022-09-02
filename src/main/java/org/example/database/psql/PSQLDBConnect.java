package org.example.database.psql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.database.DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author pashtet
 */
public class PSQLDBConnect implements DBConnect {
    private static final Logger logger = LogManager.getLogger(PSQLDBConnect.class);
    private Connection connection;

    public Connection getConnect() {
        String host = "localhost";
        String dbname = "phonebook";
        String username = "username";
        String password = "password";
        return getConnect(host, dbname, username, password);
    }

    public Connection getConnect(String host, String dbname, String username, String password) {

        StringBuilder url = new StringBuilder();
        url
                .append("jdbc:postgresql://")
                .append(host)
                .append(":5432/")
                .append(dbname);
        try {
            connection = DriverManager.getConnection(url.toString(), username, password);
        } catch (SQLException e) {
            logger.error("ErrorDB: Invalid password, username or host");
        }
        return connection;
    }
}
