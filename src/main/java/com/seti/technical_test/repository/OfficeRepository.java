package com.seti.technical_test.repository;

import com.seti.technical_test.entity.Office;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for managing Office entities.
 * Provides CRUD operations and custom query methods.
 */
public interface OfficeRepository extends R2dbcRepository<Office, Integer> {

    /**
     * Retrieves all offices belonging to a specific franchise.
     *
     * @param franchiseId the identifier of the franchise
     * @return a Flux emitting the offices associated with the franchise
     */
    Flux<Office> findByFranchiseId(Integer franchiseId);

    /**
     * Checks whether an office exists with the given name.
     *
     * @param name the office name to check
     * @return a Mono emitting true if an office exists, false otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Retrieves an office by its name.
     *
     * @param name the office name
     * @return a Mono emitting the Office if found, or empty if not
     */
    Mono<Office> findByName(String name);
}
