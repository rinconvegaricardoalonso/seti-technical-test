package com.seti.technical_test.domain.model;

import java.util.List;

/**
 * Data Transfer Object representing a franchise.
 * Used to expose franchise data through the API layer.
 *
 * @param id       the unique identifier of the franchise
 * @param name     the name of the franchise
 * @param offices  the list of offices that belong to the franchise
 */
public record Franchise(Integer id, String name, List<Office> offices) {

    public Franchise {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Franchise name cannot be null or blank");
        }

        name = name.trim().toUpperCase();
    }
}
