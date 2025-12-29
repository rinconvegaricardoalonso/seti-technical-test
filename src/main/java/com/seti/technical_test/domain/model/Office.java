package com.seti.technical_test.domain.model;

/**
 * Data Transfer Object representing an office.
 * Used to transfer office data between the API and service layers.
 *
 * @param id           the unique identifier of the office
 * @param name         the name of the office
 * @param franchiseId  the identifier of the franchise to which the office belongs
 */
public record Office(Integer id, String name, Integer franchiseId) {

    public Office {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Office name cannot be null or blank");
        }

        name = name.trim().toUpperCase();

        if (franchiseId == null) {
            throw new IllegalArgumentException("Franchise id cannot be null");
        }
    }
}
