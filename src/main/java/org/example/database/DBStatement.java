package org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.utils.IO.print;

/**
 * @author pashtet
 */
public class DBStatement {
    private static Statement statement;

    private DBStatement() {
    }

    public static Statement getStatement(final Connection connection) {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            print(e.getMessage());
        }
        return statement;
    }
}
