package org.example.utils;

/**
 * @author pashtet
 */
public class StringFormatter {
    private final int maxLength;

    public StringFormatter(int maxLength) {
        this.maxLength = maxLength;
    }

    public String centerAlign(String leftStr, String centerStr, String rightStr) {
        int cl = centerStr.length();
        if (cl % 2 != 0) cl += 1;
        int left = (maxLength - cl) / 2 + cl - 1;
        int right = maxLength - left - 1;
        String s = "%s%" + left + "s%" + right + "s";
        return String.format(s, leftStr, centerStr, rightStr);
    }

    public String leftAlign(String leftStr, String centerStr, String rightStr) {
        String s = leftStr + "%-" + (maxLength - 2) + "s" + rightStr;
        return String.format(s, centerStr);
    }

    public String rightAlign(String leftStr, String centerStr, String rightStr) {
        String s = leftStr + "%" + (maxLength - 2) + "s" + rightStr;
        return String.format(s, centerStr);

    }
}
