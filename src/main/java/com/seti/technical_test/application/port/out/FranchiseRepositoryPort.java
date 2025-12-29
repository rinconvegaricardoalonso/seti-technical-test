package com.seti.technical_test.application.port.out;

import com.seti.technical_test.domain.model.Franchise;
import reactor.core.publisher.Mono;

/**
 * Output port that defines the persistence operations required by the
 * application layer for managing {@link Franchise} aggregates.
 *
 * <p>
 * This interface represents a contract between the application/domain
 * and the infrastructure layer. It allows the business logic to interact
 * with a persistence mechanism without being coupled to any specific
 * technology (e.g. R2DBC, JPA, MongoDB).
 * </p>
 *
 * <p>
 * Implementations of this interface must be provided by the infrastructure
 * layer as adapters.
 * </p>
 */
public interface FranchiseRepositoryPort {

    /**
     * Retrieves a {@link Franchise} by its unique identifier.
     *
     * @param id the unique identifier of the franchise
     * @return a {@link Mono} emitting the franchise if found, or empty if not found
     */
    Mono<Franchise> findById(Integer id);

    /**
     * Checks whether a franchise with the given name already exists.
     *
     * @param name the franchise name to validate
     * @return a {@link Mono} emitting {@code true} if a franchise with the given
     *         name exists, {@code false} otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Retrieves a {@link Franchise} by its name.
     *
     * @param name the franchise name
     * @return a {@link Mono} emitting the franchise if found, or empty if not found
     */
    Mono<Franchise> findByName(String name);

    /**
     * Persists the given {@link Franchise} aggregate.
     *
     * <p>
     * This operation may represent either a creation or an update,
     * depending on the state of the aggregate.
     * </p>
     *
     * @param franchise the franchise aggregate to be persisted
     * @return a {@link Mono} emitting the persisted franchise
     */
    Mono<Franchise> save(Franchise franchise);
}
