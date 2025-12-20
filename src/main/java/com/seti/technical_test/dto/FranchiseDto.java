package com.seti.technical_test.dto;

import java.util.List;

/**
 * Data Transfer Object representing a franchise.
 * Used to expose franchise data through the API layer.
 *
 * @param id       the unique identifier of the franchise
 * @param name     the name of the franchise
 * @param offices  the list of offices that belong to the franchise
 */
public record FranchiseDto(Integer id, String name, List<OfficeDto> offices) {
}
