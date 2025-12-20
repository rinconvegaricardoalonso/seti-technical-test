package com.seti.technical_test.service;

import com.seti.technical_test.dto.FranchiseDto;
import com.seti.technical_test.dto.OfficeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing franchises.
 * <p>
 * Defines the contract for franchise-related business operations
 * using reactive programming.
 */
public interface IFranchiseService {

    /**
     * Retrieves a franchise by its identifier.
     *
     * @param id the franchise identifier
     * @return a {@link Mono} containing the franchise data
     */
    Mono<FranchiseDto> getFranchise(Integer id);

    /**
     * Creates a new franchise.
     *
     * @param franchiseDto the franchise data to be created
     * @return a {@link Mono} containing the created franchise
     */
    Mono<FranchiseDto> createFranchise(FranchiseDto franchiseDto);

    /**
     * Updates an existing franchise.
     *
     * @param id the franchise identifier
     * @param franchiseDto the updated franchise data
     * @return a {@link Mono} containing the updated franchise
     */
    Mono<FranchiseDto> updateFranchise(Integer id, FranchiseDto franchiseDto);

    /**
     * Retrieves all offices associated with a given franchise.
     *
     * @param franchiseId the franchise identifier
     * @return a {@link Flux} containing the franchise offices
     */
    Flux<OfficeDto> findByFranchiseId(Integer franchiseId);
}
