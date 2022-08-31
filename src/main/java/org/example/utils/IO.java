package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author pashtet
 */
public class IO {
    public static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(IO.class);

    private IO() {
        throw new IllegalStateException("Utility class");
    }

    public static void print(final String text) {
        System.out.println(text);
    }

    public static String input() {
        return scanner.nextLine();
    }

    public static String input(String text) {
        print(text);
        return input();
    }

    /**
     * This is the console clearing method and shows hi message
     */
    public static void clear(String s) {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                print("\033\143");
                System.out.flush();
            }
            print(s);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
