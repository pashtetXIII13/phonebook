package org.example.utils;

import org.example.constants.CConst;
import org.example.constants.Const;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author pashtet
 */
public class ParserTest {
    private static Map<String, String> command;

    @BeforeClass
    public static void init() {
        command = new HashMap<>();
    }

    @Test
    public void testGetCommandIsNull() {
        assertNotNull(Parser.getUserInput(""));
    }

    @Test
    public void testGetCommand() {
        List<String> list = List.of(" -f name -l name -p +79088008888", " -f name -p +7908080808", " -f name -f name", " -i s",
                " -l name -p +7908080808", " -i 1 -p +456456454", " -p 7897 -d home", " -f name -l name -d home", " -i 1 -i 2",
                " -p d", " -l name -l name", " -l v -f f -i 1 -d g -p 3", " -l gff -p +7995065050", "- i z -i z");
        list.forEach(e -> {
            String f = CConst.FIND + e;
            command = Parser.getUserInput(f);
            assertEquals(Const.ERROR_FIND, command.get(CConst.COMMAND));
            String d = CConst.DELETE + e;
            command = Parser.getUserInput(d);
            assertEquals(Const.ERROR_DELETE, command.get(CConst.COMMAND));
            String ed = CConst.EDIT + e;
            command = Parser.getUserInput(ed);
            assertEquals(Const.ERROR_EDIT, command.get(CConst.COMMAND));
            String a = CConst.ADD + e;
            command = Parser.getUserInput(a);
            assertEquals(Const.ERROR_ADD, command.get(CConst.COMMAND));
        });
    }

    @Test
    public void testEmptyParam() {
        List<String> list = List.of(" -i ", " -name", " -l ", " -p ", " -i 1 -p", "$@#@$");
        list.forEach(e -> {
            command = Parser.getUserInput(e);
            assertEquals(Const.ERROR_ILLEGAL, command.get(CConst.COMMAND));
        });
    }
}