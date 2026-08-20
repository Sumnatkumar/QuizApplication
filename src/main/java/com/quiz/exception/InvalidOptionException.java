package com.quiz.exception;

/**
 * Exception thrown when user selects an invalid option
 */

public class InvalidOptionException extends Exception {

    public InvalidOptionException(String message) {
        super(message);
    }

    public InvalidOptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
