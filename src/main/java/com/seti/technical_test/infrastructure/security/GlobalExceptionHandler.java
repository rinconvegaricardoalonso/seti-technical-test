package com.seti.technical_test.infrastructure.security;

import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the REST API.
 * <p>
 * This class centralizes the handling of application-specific exceptions
 * and maps them to appropriate HTTP responses.
 * </p>
 *
 * By using {@link RestControllerAdvice}, all exceptions thrown from
 * controllers or service layers are intercepted and converted into
 * meaningful HTTP responses with proper status codes and messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link NotFoundException} exceptions.
     * <p>
     * This exception is typically thrown when a requested resource
     * (such as a product or inventory record) cannot be found.
     * </p>
     *
     * @param ex the exception thrown by the application
     * @return a {@link ResponseEntity} with HTTP 404 (Not Found)
     *         and the exception message as the response body
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles {@link GeneralException} exceptions.
     * <p>
     * This exception represents generic business rule violations
     * or validation errors that do not fall into more specific categories.
     * </p>
     *
     * @param ex the exception thrown by the application
     * @return a {@link ResponseEntity} with HTTP 400 (Bad Request)
     *         and the exception message as the response body
     */
    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<?> handleGeneralException(GeneralException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}

