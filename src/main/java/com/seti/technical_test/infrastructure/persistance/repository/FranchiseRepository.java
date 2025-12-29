package com.seti.technical_test.infrastructure.persistance.repository;

import com.seti.technical_test.infrastructure.persistance.entity.FranchiseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for managing Franchise entities.
 * Provides CRUD operations and custom query methods.
 */
public interface FranchiseRepository extends R2dbcRepository<FranchiseEntity, Integer> {

    /**
     * Checks whether a franchise with the given name already exists.
     *
     * @param name the franchise name to check
     * @return a Mono emitting true if a franchise exists, false otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Checks whether a franchise with the given name already exists.
     *
     * @param name the franchise name to check
     * @return a Mono emitting true if a franchise exists, false otherwise
     */
    Mono<FranchiseEntity> findByName(String name);
}
