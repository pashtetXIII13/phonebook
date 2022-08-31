package org.example.database.psql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.constants.CConst;
import org.example.constants.Const;
import org.example.constants.QConst;
import org.example.constants.Tables;
import org.example.database.ConnectionPool;
import org.example.database.DBStatement;
import org.example.database.Executor;
import org.example.utils.StringFormatter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.utils.IO.input;
import static org.example.utils.IO.print;

/**
 * @author pashtet
 */
public class ExecutorPSQL implements Executor {
    private static final Logger logger = LogManager.getLogger(ExecutorPSQL.class);
    private final Connection connection;
    private final Statement statement;

    public ExecutorPSQL(ConnectionPool connectionPool) {
        connection = connectionPool.getConnection();
        statement = DBStatement.getStatement(connection);
        Tables.create(statement);
    }

    public String query(final Map<String, String> command) throws SQLException {
        StringBuilder queryStr;
        StringBuilder result = new StringBuilder();
        queryStr = new StringBuilder();
        StringFormatter stringFormatter = new StringFormatter(Const.MAX_LENGTH);
        StringFormatter stringFormatterQuarter = new StringFormatter(Const.MAX_LENGTH / 4);
        if (command.get(CConst.COMMAND).equals(QConst.SELECT)) {
            String orAnd = command.containsKey(QConst.FIRST_NAME) && command.containsKey(QConst.LAST_NAME) ? "and" : "or";
            ResultSet resultSet = select(command, orAnd);
            if (!resultSet.isBeforeFirst()) return Const.NOT_FOUND;
            result.append(Const.H_LINE).append("\n")
                    .append(Const.FIRST_NAME)
                    .append(Const.LAST_NAME)
                    .append(Const.PHONE)
                    .append(Const.DESCRIPTION).append("\n")
                    .append(Const.H_LINE).append("\n");
            while (resultSet.next()) {
                result.append(stringFormatterQuarter.centerAlign("|", resultSet.getInt(1)
                        + "." + resultSet.getString(2), Const.EMPTY));
                result.append(stringFormatterQuarter.centerAlign(Const.V_LINE, resultSet.getString(3), Const.EMPTY));
                result.append(stringFormatterQuarter.centerAlign(Const.V_LINE, resultSet.getString(5), Const.EMPTY));
                result.append(stringFormatterQuarter.centerAlign(Const.V_LINE, resultSet.getString(6), Const.V_LINE));
                result.append("\n");
                result.append(Const.H_LINE).append("\n");
            }
        } else if (command.get(CConst.COMMAND).equals(QConst.INSERT)) {
            result.append(insert(command, select(command, "and")));
        } else if (command.get(CConst.COMMAND).equals(QConst.DELETE)) {
            result.append(delete(command));
        } else if (command.get(CConst.COMMAND).equals(QConst.UPDATE)) {
            StringBuilder column = new StringBuilder();
            String phoneNumStr = "";
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(queryStr.append(QConst.SELECT).append(" from ")
                    .append(Tables.TABLE_PHONE).append(" where ").append(QConst.ID).append(" = '")
                    .append(command.get(QConst.ID)).append("';").toString());
            List<String> list = new ArrayList<>();
            while (resultSet.next()) list.add(QConst.PHONE_NUMBER + " = '" + resultSet.getString(2) + "'");
            phoneNumStr = list.get(0);
            if (list.size() > 1) {
                print(stringFormatter.rightAlign(Const.EMPTY, "more than one entry found", Const.EMPTY));
                for (int i = 0; i < list.size(); i++) {
                    print(stringFormatter.rightAlign(Const.EMPTY, i + 1 + "." + list.get(i), Const.EMPTY));
                }
                int i;
                do {
                    i = Integer.parseInt(input(stringFormatter.rightAlign(Const.EMPTY, "", Const.EMPTY)));
                }
                while (i < 1 || i > list.size());
                phoneNumStr = list.get(--i);
            }
            String firstName = input(stringFormatter.rightAlign(Const.EMPTY, Const.ENTER_FIRST_NAME, Const.EMPTY));
            String lastName = input(stringFormatter.rightAlign(Const.EMPTY, Const.ENTER_LAST_NAME, Const.EMPTY));
            if (!firstName.isEmpty()) {
                firstName = cleanParam(firstName);
                column.append(QConst.FIRST_NAME).append(" = '").append(firstName).append("' ");
            }
            if (!lastName.isEmpty()) {
                lastName = cleanParam(lastName);
                if (column.length() > 0) column.append(",");
                column.append(QConst.LAST_NAME).append(" = '").append(lastName).append("' ");
            }

            if (column.length() > 0) {
                String val = QConst.ID + " = " + command.get(QConst.ID);
                result.append(update(Tables.TABLE_USERS, column.toString(), val));
            }
            column = new StringBuilder();
            String phNumber = input(stringFormatter.rightAlign(Const.EMPTY, Const.ENTER_PHONE, Const.EMPTY));
            String desc = input(stringFormatter.rightAlign(Const.EMPTY, Const.ENTER_DESCRIPTION, Const.EMPTY));
            if (!phNumber.isEmpty()) {
                phNumber = "+" + phNumber.replaceAll("\\D+", "");
                column.append(QConst.PHONE_NUMBER).append(" = '").append(phNumber).append("' ");
            }
            if (!desc.isEmpty()) {
                if (column.length() > 0) column.append(",");
                desc = cleanParam(desc);
                column.append(QConst.DESCRIPTION).append(" = '").append(desc).append("' ");
            }
            if (column.length() > 0) {
                String val = QConst.ID + " = " + command.get(QConst.ID) + " and "
                        + phoneNumStr;
                result.append(update(Tables.TABLE_PHONE, column.toString(), val));
            }
            connection.commit();
        }
        return result.toString();
    }

    private ResultSet select(final Map<String, String> command, final String orAnd) throws SQLException {
        command.put(CConst.COMMAND, QConst.SELECT);
        StringBuilder queryStr = new StringBuilder();
        return statement.executeQuery(queryStr.append(command.get(CConst.COMMAND))
                .append("from ").append(Tables.TABLE_USERS).append(", ")
                .append(Tables.TABLE_PHONE).append(" where ").append(Tables.TABLE_USERS)
                .append(".").append(QConst.ID).append(" = ")
                .append(Tables.TABLE_PHONE).append(".").append(QConst.ID).append(" and ((")
                .append(QConst.FIRST_NAME).append(" = '").append(command.get(QConst.FIRST_NAME))
                .append("' ").append(orAnd).append(" ").append(QConst.LAST_NAME).append(" = '")
                .append(command.get(QConst.LAST_NAME)).append("') or ").append(QConst.PHONE_NUMBER)
                .append(" = '").append(command.get(QConst.PHONE_NUMBER)).append("');").toString());
    }

    private String insert(final Map<String, String> command, ResultSet select) throws SQLException {
        int id;
        command.put(CConst.COMMAND, QConst.INSERT);
        try {
            connection.setAutoCommit(false);
            if (!select.isBeforeFirst()) {
                StringBuilder queryStr = new StringBuilder();
                ResultSet insert = statement.executeQuery(queryStr.append(command.get(CConst.COMMAND))
                        .append("into ").append(Tables.TABLE_USERS).append(" (")
                        .append(QConst.FIRST_NAME).append(", ").append(QConst.LAST_NAME)
                        .append(") values ('").append(command.get(QConst.FIRST_NAME)).append("', '")
                        .append(command.get(QConst.LAST_NAME)).append("') returning ").append(QConst.ID).append(";")
                        .toString());
                insert.next();
                id = insert.getInt(1);
            } else {
                select.next();
                id = select.getInt(1);
            }
            StringBuilder queryStr = new StringBuilder();
            statement.executeUpdate(queryStr.append(command.get(CConst.COMMAND)).append("into ")
                    .append(Tables.TABLE_PHONE).append(" values (").append(id).append(",'")
                    .append(command.get(QConst.PHONE_NUMBER)).append("', '")
                    .append(command.get(QConst.DESCRIPTION)).append("');").toString());
            connection.commit();
            return Const.ENTRY_ADDED;
        } catch (SQLException e) {
            connection.rollback();
            logger.error(e.getMessage());
            return Const.NUMBER_EXIST;
        }
    }

    private String delete(final Map<String, String> command) throws SQLException {
        StringBuilder queryStr = new StringBuilder();
        int result = statement.executeUpdate(queryStr.append(command.get(CConst.COMMAND))
                .append("from ").append(Tables.TABLE_USERS).append(" where ")
                .append(Tables.TABLE_USERS).append(".").append(QConst.ID).append(" = ")
                .append(command.get(QConst.ID)).append(";").toString());
        if (result == 0) return Const.DELETE_NOT_EXIST;
        return Const.DELETE_OK;
    }

    private String update(String table, String column, String val) throws SQLException {
        StringBuilder queryStr = new StringBuilder();
        int result = statement.executeUpdate(queryStr.append(QConst.UPDATE)
                .append(table)
                .append(" set ")
                .append(column)
                .append(" where ")
                .append(val)
                .toString());
        return result > 0 ? Const.UPDATE_OK : Const.UPDATE_BAD;
    }

    private String cleanParam(String param) {
        param = param.replaceAll("[^A-Za-z0-9А-Яа-яЁё]+", "");
        return param.substring(0, 1).toUpperCase() + param.substring(1);
    }
}
