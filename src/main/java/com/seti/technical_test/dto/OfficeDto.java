package com.seti.technical_test.dto;

/**
 * Data Transfer Object representing an office.
 * Used to transfer office data between the API and service layers.
 *
 * @param id           the unique identifier of the office
 * @param name         the name of the office
 * @param franchiseId  the identifier of the franchise to which the office belongs
 */
public record OfficeDto(Integer id, String name, Integer franchiseId) {
}
