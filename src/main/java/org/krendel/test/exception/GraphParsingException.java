package org.krendel.test.exception;

/**
 * Generic graph exception class
 */
public class GraphParsingException extends RuntimeException {
    public GraphParsingException(String message) {
        super(message);
    }
}
