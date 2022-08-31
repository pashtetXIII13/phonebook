package org.example.constants;

import java.sql.SQLException;
import java.sql.Statement;

import static org.example.utils.IO.print;

/**
 * This class creates tables if they don`t exist
 *
 * @author pashtet.
 */
public final class Tables {
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PHONE = "phone";
    private static final String USER = "create table if not exists " + TABLE_USERS + " "
            + "(" + QConst.ID + " serial primary key, " + QConst.FIRST_NAME + " varchar(255) not null,"
            + QConst.LAST_NAME + " varchar(255) not null);";
    private static final String PHONE = "create table if not exists " + TABLE_PHONE + " "
            + "(" + QConst.ID + " int not null, number varchar(30) not null unique,"
            + QConst.DESCRIPTION + " varchar(255), foreign key (" + QConst.ID + ") references "
            + TABLE_USERS + "(" + QConst.ID + ") on delete cascade);";

    private Tables() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean create(final Statement statement) {
        try {
            statement.execute(USER);
            statement.execute(PHONE);
            return true;
        } catch (SQLException e) {
            print(e.getMessage());
            return false;
        }
    }
}
