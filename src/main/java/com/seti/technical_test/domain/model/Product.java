package com.seti.technical_test.domain.model;

/**
 * Data Transfer Object representing a product.
 * Used to transfer product data between the API and service layers.
 *
 * @param id        the unique identifier of the product
 * @param name      the name of the product
 * @param stock     the available stock of the product
 * @param officeId  the identifier of the office to which the product belongs
 */
public record Product(Integer id, String name, Integer stock, Integer officeId) {

    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }

        name = name.trim().toUpperCase();

        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative");
        }
    }
}
