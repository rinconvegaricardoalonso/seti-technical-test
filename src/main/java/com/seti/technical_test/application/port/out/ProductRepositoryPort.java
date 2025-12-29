package com.seti.technical_test.application.port.out;

import com.seti.technical_test.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Output port that defines the persistence operations required by the
 * application layer for managing {@link Product} aggregates.
 *
 * <p>
 * This interface abstracts the underlying persistence mechanism, allowing
 * the application and domain layers to remain decoupled from infrastructure
 * concerns such as databases or specific data access technologies.
 * </p>
 *
 * <p>
 * Implementations of this port must be provided by the infrastructure layer
 * as adapters.
 * </p>
 */
public interface ProductRepositoryPort {

    /**
     * Retrieves a {@link Product} by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return a {@link Mono} emitting the product if found, or empty if not found
     */
    Mono<Product> findById(Integer id);

    /**
     * Checks whether a product with the given name already exists.
     *
     * <p>
     * This method is commonly used to enforce business rules such as
     * product name uniqueness.
     * </p>
     *
     * @param name the product name to validate
     * @return a {@link Mono} emitting {@code true} if a product with the given
     *         name exists, {@code false} otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Retrieves a {@link Product} by its name.
     *
     * @param name the product name
     * @return a {@link Mono} emitting the product if found, or empty if not found
     */
    Mono<Product> findByName(String name);

    /**
     * Persists the given {@link Product} aggregate.
     *
     * <p>
     * This operation may represent either a creation or an update,
     * depending on the current state of the aggregate.
     * </p>
     *
     * @param product the product aggregate to be persisted
     * @return a {@link Mono} emitting the persisted product
     */
    Mono<Product> save(Product product);

    /**
     * Deletes the given {@link Product} aggregate.
     *
     * <p>
     * The deletion behavior depends on the underlying persistence
     * implementation (e.g. hard delete or soft delete).
     * </p>
     *
     * @param product the product aggregate to be deleted
     * @return a {@link Mono} that completes when the deletion is finished
     */
    Mono<Void> delete(Product product);

    /**
     * Retrieves the products with the highest stock for a given office or franchise.
     *
     * <p>
     * This method is typically used for reporting or inventory-related
     * use cases.
     * </p>
     *
     * @param franchiseId the unique identifier of the franchise or office
     * @return a {@link Flux} emitting the products with the highest stock
     */
    Flux<Product> findTopStockByOffice(Integer franchiseId);
}
