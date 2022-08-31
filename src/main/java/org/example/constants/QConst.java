package org.example.constants;

/**
 * @author pashtet
 */
public class QConst {
    public static final String SELECT = "select *";
    public static final String INSERT = "insert ";
    public static final String UPDATE = "update ";
    public static final String DELETE = "delete ";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PHONE_NUMBER = "number";
    public static final String ID = "id";
    public static final String DESCRIPTION = "description";

    private QConst() {
        throw new IllegalStateException("Utility class");
    }
}
