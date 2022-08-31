package org.example.phonebook;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.constants.CConst;
import org.example.constants.Const;
import org.example.database.ConnectionPool;
import org.example.database.Executor;
import org.example.database.psql.ExecutorPSQL;
import org.example.database.psql.PSQLDBConnect;
import org.example.utils.Parser;

import java.util.Map;

import static org.example.utils.IO.*;

/**
 * @author pashtet
 * @version 1.0
 */
public class Phonebook {
    private static final Logger logger = LogManager.getLogger(Phonebook.class);

    private Phonebook() {
        throw new IllegalStateException("Utility class");
    }

    public static void run() {
        ConnectionPool connectionPool = ConnectionPool.create(new PSQLDBConnect());
        final Executor executor = new ExecutorPSQL(connectionPool);
        clear(Const.HI);
        while (true) {
            final Map<String, String> userInput = Parser.getUserInput(input(Const.ENTER_COMMAND));
            if (userInput.get(CConst.COMMAND).strip().startsWith(Const.ERROR)) print(userInput.get(CConst.COMMAND));
            else if (userInput.get(CConst.COMMAND).equals(CConst.EXIT)) break;
            else if (userInput.get(CConst.COMMAND).equals(CConst.CLEAR)) clear(Const.HI);
            else if (userInput.get(CConst.COMMAND).equals(CConst.HELP)) print(Const.HELP);
            else {
                try {
                    print(executor.query(userInput));
                } catch (Exception e) {
                    connectionPool.close();
                    logger.error(e.getMessage());
                }
            }
        }

    }
}
