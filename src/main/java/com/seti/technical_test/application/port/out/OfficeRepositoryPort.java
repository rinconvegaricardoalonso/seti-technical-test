package com.seti.technical_test.application.port.out;

import com.seti.technical_test.domain.model.Office;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Output port that defines the persistence operations required by the
 * application layer for managing {@link Office} aggregates.
 *
 * <p>
 * This interface represents an abstraction over the persistence mechanism,
 * allowing the application and domain layers to remain independent from
 * infrastructure-specific technologies such as Spring Data R2DBC.
 * </p>
 *
 * <p>
 * Concrete implementations of this port must be provided by the
 * infrastructure layer as adapters.
 * </p>
 */
public interface OfficeRepositoryPort {

    /**
     * Retrieves an {@link Office} by its unique identifier.
     *
     * @param id the unique identifier of the office
     * @return a {@link Mono} emitting the office if found, or empty if not found
     */
    Mono<Office> findById(Integer id);

    /**
     * Checks whether an office with the given name already exists.
     *
     * <p>
     * This method is typically used to enforce business constraints such as
     * name uniqueness before creating or updating an office.
     * </p>
     *
     * @param name the office name to validate
     * @return a {@link Mono} emitting {@code true} if an office with the given
     *         name exists, {@code false} otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Retrieves an {@link Office} by its name.
     *
     * @param name the office name
     * @return a {@link Mono} emitting the office if found, or empty if not found
     */
    Mono<Office> findByName(String name);

    /**
     * Persists the given {@link Office} aggregate.
     *
     * <p>
     * This operation may represent either a creation or an update,
     * depending on the current state of the aggregate.
     * </p>
     *
     * @param office the office aggregate to be persisted
     * @return a {@link Mono} emitting the persisted office
     */
    Mono<Office> save(Office office);

    /**
     * Retrieves all {@link Office} instances associated with a given franchise.
     *
     * @param franchiseId the unique identifier of the franchise
     * @return a {@link Flux} emitting the offices belonging to the specified franchise
     */
    Flux<Office> findByFranchiseId(Integer franchiseId);
}
