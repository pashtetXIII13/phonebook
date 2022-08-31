package org.example.exception;

/**
 * @author pashtet
 */
public class ConnectionPoolEmpty extends Exception {
    public ConnectionPoolEmpty(String message) {
        super(message);
    }
}
