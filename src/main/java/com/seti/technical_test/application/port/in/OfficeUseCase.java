package com.seti.technical_test.application.port.in;

import com.seti.technical_test.domain.model.Office;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing offices.
 * <p>
 * Defines the contract for office-related business operations
 * using reactive programming.
 */
public interface OfficeUseCase {

    /**
     * Retrieves an office by its identifier.
     *
     * @param id the office identifier
     * @return a {@link Mono} containing the office data
     */
    Mono<Office> getOffice(Integer id);

    /**
     * Creates a new office.
     *
     * @param office the office data to be created
     * @return a {@link Mono} containing the created office
     */
    Mono<Office> createOffice(Office office);

    /**
     * Updates an existing office.
     *
     * @param id the office identifier
     * @param office the updated office data
     * @return a {@link Mono} containing the updated office
     */
    Mono<Office> updateOffice(Integer id, Office office);
}
