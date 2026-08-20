package com.quiz.exception;

/**
 * Exception thrown when there's an issue with quiz data
 */

public class QuizDataException extends Exception {

    public QuizDataException(String message) {
        super(message);
    }

    public QuizDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
