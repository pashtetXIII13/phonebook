package org.example.database;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author pashtet
 */
public interface Executor {
    String query(Map<String, String> command) throws SQLException;
}
