package com.seti.technical_test.infrastructure.exception;

/**
 * Generic exception used to represent unexpected or uncategorized errors
 * occurring within the application.
 * <p>
 * This exception allows services or controllers to signal that an operation
 * has failed for a reason that does not fall under more specific exception
 * types, providing a flexible way to handle unforeseen cases.
 */
public class GeneralException extends RuntimeException {

    /**
     * Creates a new GeneralException with the specified detail message.
     *
     * @param message a descriptive message explaining the cause of the exception
     */
    public GeneralException(String message) {
        super(message);
    }
}
