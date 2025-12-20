package com.seti.technical_test.service;

import com.seti.technical_test.dto.OfficeDto;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing offices.
 * <p>
 * Defines the contract for office-related business operations
 * using reactive programming.
 */
public interface IOfficeService {

    /**
     * Retrieves an office by its identifier.
     *
     * @param id the office identifier
     * @return a {@link Mono} containing the office data
     */
    Mono<OfficeDto> getOffice(Integer id);

    /**
     * Creates a new office.
     *
     * @param officeDto the office data to be created
     * @return a {@link Mono} containing the created office
     */
    Mono<OfficeDto> createOffice(OfficeDto officeDto);

    /**
     * Updates an existing office.
     *
     * @param id the office identifier
     * @param officeDto the updated office data
     * @return a {@link Mono} containing the updated office
     */
    Mono<OfficeDto> updateOffice(Integer id, OfficeDto officeDto);
}
