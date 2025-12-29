package com.seti.technical_test.application.port.in;

import com.seti.technical_test.domain.model.Franchise;
import com.seti.technical_test.domain.model.Office;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing franchises.
 * <p>
 * Defines the contract for franchise-related business operations
 * using reactive programming.
 */
public interface FranchiseUseCase {

    /**
     * Retrieves a franchise by its identifier.
     *
     * @param id the franchise identifier
     * @return a {@link Mono} containing the franchise data
     */
    Mono<Franchise> getFranchise(Integer id);

    /**
     * Creates a new franchise.
     *
     * @param franchise the franchise data to be created
     * @return a {@link Mono} containing the created franchise
     */
    Mono<Franchise> createFranchise(Franchise franchise);

    /**
     * Updates an existing franchise.
     *
     * @param id the franchise identifier
     * @param franchise the updated franchise data
     * @return a {@link Mono} containing the updated franchise
     */
    Mono<Franchise> updateFranchise(Integer id, Franchise franchise);

    /**
     * Retrieves all offices associated with a given franchise.
     *
     * @param franchiseId the franchise identifier
     * @return a {@link Flux} containing the franchise offices
     */
    Flux<Office> findByFranchiseId(Integer franchiseId);
}
