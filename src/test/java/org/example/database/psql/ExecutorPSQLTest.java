package org.example.database.psql;

import org.example.constants.CConst;
import org.example.constants.Const;
import org.example.constants.QConst;
import org.example.database.ConnectionPool;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author pashtet
 */
public class ExecutorPSQLTest {
    private static ExecutorPSQL executor;

    @BeforeClass
    public static void init() {
        executor = new ExecutorPSQL(ConnectionPool.create(new PSQLDBConnect()));
    }

    @Test
    public void testQueryNotFound() throws SQLException {
        Map<String, String> map = new HashMap<>();
        map.put(CConst.COMMAND, QConst.SELECT);
        map.put(QConst.FIRST_NAME, "fff");
        assertEquals(Const.NOT_FOUND, executor.query(map));
    }

}
