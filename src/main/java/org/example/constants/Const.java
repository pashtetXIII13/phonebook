package org.example.constants;

import org.example.utils.StringFormatter;

/**
 * @author pashtet
 */
public final class Const {
    public static final int MAX_LENGTH = 144;  // must be a multiple of four
    public static final String H_LINE;
    public static final String V_LINE;
    public static final String HELLO;
    public static final String VERSION;
    public static final String NOT_FOUND;
    public static final String ENTER_COMMAND;
    public static final String FIRST_NAME;
    public static final String LAST_NAME;
    public static final String PHONE;
    public static final String DESCRIPTION;
    public static final String EMPTY;
    public static final String HI;
    public static final String ERROR;
    public static final String HELP;
    public static final String SEE_HELP;
    public static final String ERROR_ADD;
    public static final String ERROR_FIND;
    public static final String ERROR_DELETE;
    public static final String ERROR_EDIT;
    public static final String ERROR_ILLEGAL;
    public static final String ENTRY_ADDED;
    public static final String NUMBER_EXIST;
    public static final String DELETE_NOT_EXIST;
    public static final String DELETE_OK;
    public static final String ENTER_FIRST_NAME;
    public static final String ENTER_LAST_NAME;
    public static final String ENTER_PHONE;
    public static final String ENTER_DESCRIPTION;
    public static final String OR_PRESS_ENTER;
    public static final String UPDATE_OK;
    public static final String UPDATE_BAD;

    static {
        StringFormatter stringFormatter = new StringFormatter(MAX_LENGTH);
        StringFormatter stringFormatterQuarter = new StringFormatter(MAX_LENGTH / 4);
        StringFormatter stringFormatterHalf = new StringFormatter(MAX_LENGTH / 2);
        EMPTY = " ";
        SEE_HELP = " see help ";
        ERROR = "!!Error command!!";
        OR_PRESS_ENTER = "or press enter key";
        ENTER_FIRST_NAME = "Enter new first name " + OR_PRESS_ENTER;
        ENTER_LAST_NAME = "Enter new last name " + OR_PRESS_ENTER;
        ENTER_PHONE = "Enter new phone number " + OR_PRESS_ENTER;
        ENTER_DESCRIPTION = "Enter new description " + OR_PRESS_ENTER;
        ENTRY_ADDED = stringFormatter.rightAlign(EMPTY, "::Good::Entry added", EMPTY);
        NUMBER_EXIST = stringFormatter.rightAlign(EMPTY, ERROR + " Phone number already exists", EMPTY);
        DELETE_NOT_EXIST = stringFormatter.rightAlign(EMPTY, ERROR + " Entry does not exist", EMPTY);
        DELETE_OK = stringFormatter.rightAlign(EMPTY, "::Good::Entry deleted", EMPTY);
        UPDATE_OK = stringFormatter.rightAlign(EMPTY, "::Good::Entry update", EMPTY);
        UPDATE_BAD = stringFormatter.rightAlign(EMPTY, "Nothing has been updated", EMPTY);
        String fs = "%" + (MAX_LENGTH - 2) + "s";
        String str = String.format(fs, "").replace(" ", "-");
        H_LINE = stringFormatter.centerAlign("+", str, "+");
        V_LINE = "|";
        HELLO = stringFormatter.centerAlign(V_LINE, "Hello!", V_LINE);
        VERSION = stringFormatter.centerAlign(V_LINE, "Phone Book v1.0 ", V_LINE);
        NOT_FOUND = stringFormatter.rightAlign(EMPTY, "!!!Not found!!!", EMPTY);
        ENTER_COMMAND = stringFormatter.rightAlign(EMPTY, "::Enter command::", EMPTY);
        FIRST_NAME = stringFormatterQuarter.centerAlign(V_LINE, "First Name", EMPTY);
        LAST_NAME = stringFormatterQuarter.centerAlign(V_LINE, "Last Name", EMPTY);
        PHONE = stringFormatterQuarter.centerAlign(V_LINE, "Phone Number", EMPTY);
        DESCRIPTION = stringFormatterQuarter.centerAlign(V_LINE, "Description", V_LINE);
        HI = H_LINE + "\n" + HELLO + "\n" + VERSION + "\n" + H_LINE;
        ERROR_ILLEGAL = stringFormatter.rightAlign(EMPTY, ERROR, EMPTY);
        ERROR_ADD = stringFormatter.rightAlign(EMPTY, ERROR + SEE_HELP + CConst.ADD, EMPTY);
        ERROR_FIND = stringFormatter.rightAlign(EMPTY, ERROR + SEE_HELP + CConst.FIND, EMPTY);
        ERROR_DELETE = stringFormatter.rightAlign(EMPTY, ERROR + SEE_HELP + CConst.DELETE, EMPTY);
        ERROR_EDIT = stringFormatter.rightAlign(EMPTY, ERROR + SEE_HELP + CConst.EDIT, EMPTY);
        String helpFind = stringFormatterHalf.leftAlign(V_LINE, EMPTY + CConst.FIND
                + " - searches for a contact by name or(and) last name or number.", EMPTY);
        String exFind = stringFormatterHalf.leftAlign(V_LINE, " find -f Иван, find -l"
                + "Иванов, find -p +77777777777 ", V_LINE);
        String helpTitle1 = stringFormatterHalf.centerAlign(V_LINE, "Commands:", EMPTY);
        String helpTitle2 = stringFormatterHalf.centerAlign(V_LINE, "Example:", V_LINE);
        String helpTitle = H_LINE + "\n" + helpTitle1 + helpTitle2 + "\n";
        String helpAdd = stringFormatterHalf.leftAlign(V_LINE, EMPTY + CConst.ADD
                + " - saves a new contact entry into the phone book.", EMPTY);
        String exAdd = stringFormatterHalf.leftAlign(V_LINE, EMPTY + CConst.ADD
                + " -f Иван -l Иванов -p +77777777777 ", V_LINE);
        String helpEdit = stringFormatterHalf.leftAlign(V_LINE, EMPTY
                + CConst.EDIT + " - modifies an existing contact.", EMPTY);
        String exEdit = stringFormatterHalf.leftAlign(V_LINE, EMPTY + CConst.EDIT + " -i 1", V_LINE);
        String helpDelete = stringFormatterHalf.leftAlign(V_LINE, EMPTY
                + CConst.DELETE + "- removes a contact from the phone book.", EMPTY);
        String exDelete = stringFormatterHalf.leftAlign(V_LINE, EMPTY + CConst.DELETE + " -i 1 ", V_LINE);
        String helpHelp = stringFormatterHalf.leftAlign(V_LINE, EMPTY
                + "help - lists all valid commands.", EMPTY);
        String exHelp = stringFormatterHalf.leftAlign(V_LINE, EMPTY + "help ", V_LINE);
        String helpClear = stringFormatterHalf.leftAlign(V_LINE, EMPTY + "clear - clears the console.", EMPTY);
        String exClear = stringFormatterHalf.leftAlign(V_LINE, EMPTY + "clear ", V_LINE);
        String helpExit = stringFormatterHalf.leftAlign(V_LINE, EMPTY + "exit - exit from the program.", EMPTY);
        String exExit = stringFormatterHalf.leftAlign(V_LINE, EMPTY + "exit", V_LINE);
        HELP = helpTitle + H_LINE + "\n" + helpFind + exFind + "\n" + helpAdd + exAdd + "\n" + helpEdit + exEdit + "\n"
                + helpDelete + exDelete + "\n" + helpHelp + exHelp + "\n" + helpClear + exClear + "\n"
                + helpExit + exExit + "\n" + H_LINE;
    }

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
