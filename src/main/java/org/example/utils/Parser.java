package org.example.utils;

import org.example.constants.CConst;
import org.example.constants.Const;
import org.example.constants.QConst;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pashtet
 */
public class Parser {

    Parser() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, String> getUserInput(final String userInput) {
        // TODO Refactor this method to reduce its Cognitive Complexity.
        Map<String, String> map = new HashMap<>();

        if (!userInput.matches("[A-Za-z0-9А-Яа-яЁё+ -]+$")) {
            map.put(CConst.COMMAND, Const.ERROR_ILLEGAL);
            return map;
        }
        String string = userInput.toLowerCase().replaceAll("[^A-Za-z0-9А-Яа-яЁё-]+", "");

        Arrays.stream(string.split("-", 6)).forEach(s -> {
            if (s.equals(CConst.FIND)) map.put(CConst.COMMAND, QConst.SELECT);
            else if (s.equals(CConst.ADD)) map.put(CConst.COMMAND, QConst.INSERT);
            else if (s.equals(CConst.EDIT)) map.put(CConst.COMMAND, QConst.UPDATE);
            else if (s.equals(CConst.DELETE)) map.put(CConst.COMMAND, QConst.DELETE);
            else if (s.equals(CConst.CLEAR) || s.equals(CConst.EXIT) || s.equals(CConst.HELP))
                map.put(CConst.COMMAND, s);
            else if (s.startsWith("f")) {
                map.compute(QConst.FIRST_NAME, (k, v) -> (v == null) ? clearParam(s) : null);
            } else if (s.startsWith("l")) {
                map.compute(QConst.LAST_NAME, (k, v) -> (v == null) ? clearParam(s) : null);
            } else if (s.startsWith("p")) {
                String temp = clearParam(s);
                map.compute(QConst.PHONE_NUMBER, (k, v) -> (v == null) && temp.matches("\\d+") ? "+" + temp : null);
            } else if (s.startsWith("i")) {
                String temp = clearParam(s);
                map.compute(QConst.ID, (k, v) -> (v == null) && temp.matches("\\d+") ? temp : null);
            } else if (s.startsWith("d")) {
                map.compute(QConst.DESCRIPTION, (k, v) -> map.get(QConst.DESCRIPTION) == null ? clearParam(s) : null);
            }
        });

        if (map.computeIfAbsent(CConst.COMMAND, key -> Const.ERROR_ILLEGAL).equals(Const.ERROR_ILLEGAL)) {
            return map;
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals("null")) map.put(CConst.COMMAND, Const.ERROR_ILLEGAL);
        }

        if (map.get(CConst.COMMAND).equals(QConst.SELECT) && map.containsKey(QConst.PHONE_NUMBER) && map.size() != 2) {
            map.put(CConst.COMMAND, Const.ERROR_FIND);
        }

        if (map.get(CConst.COMMAND).equals(QConst.SELECT) && map.containsKey(QConst.FIRST_NAME) &&
                map.containsKey(QConst.LAST_NAME) && map.size() > 3) {
            map.put(CConst.COMMAND, Const.ERROR_FIND);
        }

        if (map.get(CConst.COMMAND).equals(QConst.SELECT) && map.size() != 2
                && (map.containsKey(QConst.FIRST_NAME) || map.containsKey(QConst.LAST_NAME))) {
            map.put(CConst.COMMAND, Const.ERROR_FIND);
        }

        if (map.get(CConst.COMMAND).equals(QConst.SELECT) && map.size() < 2) {
            map.put(CConst.COMMAND, Const.ERROR_FIND);
        }

        if (map.get(CConst.COMMAND).equals(QConst.INSERT) && (map.size() < 5 || map.containsKey(QConst.ID))) {
            map.put(CConst.COMMAND, Const.ERROR_ADD);
        }

        if (map.get(CConst.COMMAND).equals(QConst.UPDATE) && (map.size() > 2 || !map.containsKey(QConst.ID))) {
            map.put(CConst.COMMAND, Const.ERROR_EDIT);
        }

        if (map.get(CConst.COMMAND).equals(QConst.DELETE) && (map.size() > 2 || !map.containsKey(QConst.ID))) {
            map.put(CConst.COMMAND, Const.ERROR_DELETE);
        }

        return map;
    }

    private static String clearParam(String string) {
        if (string.length() < 2) {
            return "null";
        }
        return string.substring(1, 2).toUpperCase() + string.substring(2);
    }
}
