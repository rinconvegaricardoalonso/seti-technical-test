package com.seti.technical_test.infrastructure.persistance.repository;

import com.seti.technical_test.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for managing {@link ProductEntity} entities.
 * <p>
 * This repository uses Spring Data R2DBC to perform non-blocking
 * database operations over the Product table.
 */
public interface ProductRepository extends R2dbcRepository<ProductEntity, Integer> {

    /**
     * Retrieves the product with the highest stock for each office
     * that belongs to the given franchise.
     * <p>
     * The query uses {@code DISTINCT ON (o.id)} combined with
     * {@code ORDER BY p.stock DESC} to ensure that only the product
     * with the maximum stock per office is returned.
     *
     * @param franchiseId the identifier of the franchise
     * @return a reactive stream ({@link Flux}) containing the products
     *         with the highest stock per office
     */
    @Query("""
        SELECT DISTINCT ON (o.id)
               p.id,
               p.name,
               p.stock,
               p.office_id
        FROM product p
        JOIN office o ON o.id = p.office_id
        WHERE o.franchise_id = :franchiseId
        ORDER BY o.id, p.stock DESC
    """)
    Flux<ProductEntity> findTopStockByOffice(Integer franchiseId);

    /**
     * Checks whether a product with the given name exists.
     *
     * @param name the product name to look for
     * @return a {@link Mono} emitting {@code true} if the product exists,
     *         or {@code false} otherwise
     */
    Mono<Boolean> existsByName(String name);

    /**
     * Finds a product by its name.
     *
     * @param name the name of the product
     * @return a {@link Mono} emitting the found product,
     *         or empty if no product matches the given name
     */
    Mono<ProductEntity> findByName(String name);
}
