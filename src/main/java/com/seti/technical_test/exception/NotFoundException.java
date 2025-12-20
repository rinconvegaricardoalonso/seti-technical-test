package com.seti.technical_test.exception;

/**
 * Exception thrown when a requested resource cannot be found in the system.
 * <p>
 * Typically used to indicate that an entity or record with the specified
 * identifier does not exist, allowing services and controllers to communicate
 * clear error conditions.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Creates a new NotFoundException with the specified detail message.
     *
     * @param message a descriptive message explaining the reason for the exception
     */
    public NotFoundException(String message) {
        super(message);
    }
}

