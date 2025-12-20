package com.seti.technical_test.dto;

/**
 * Data Transfer Object representing a product.
 * Used to transfer product data between the API and service layers.
 *
 * @param id        the unique identifier of the product
 * @param name      the name of the product
 * @param stock     the available stock of the product
 * @param officeId  the identifier of the office to which the product belongs
 */
public record ProductDto(Integer id, String name, Integer stock, Integer officeId) {
}
