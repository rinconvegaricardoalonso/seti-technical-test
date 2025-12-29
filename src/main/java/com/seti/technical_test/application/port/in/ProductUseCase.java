package com.seti.technical_test.application.port.in;

import com.seti.technical_test.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing products.
 * <p>
 * Defines the contract for product-related business operations
 * using reactive programming.
 */
public interface ProductUseCase {

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the product identifier
     * @return a {@link Mono} containing the product data
     */
    Mono<Product> getProduct(Integer id);

    /**
     * Creates a new product.
     *
     * @param product the product data to be created
     * @return a {@link Mono} containing the created product
     */
    Mono<Product> createProduct(Product product);

    /**
     * Updates an existing product.
     *
     * @param id the product identifier
     * @param product the updated product data
     * @return a {@link Mono} containing the updated product
     */
    Mono<Product> updateProduct(Integer id, Product product);

    /**
     * Deletes a product by its identifier.
     *
     * @param id the product identifier
     * @return a {@link Mono} signaling completion of the operation
     */
    Mono<Void> deleteProduct(Integer id);

    /**
     * Retrieves the products with the highest stock per office
     * for a given franchise.
     *
     * @param franchiseId the franchise identifier
     * @return a {@link Flux} containing the top products per office
     */
    Flux<Product> getTopProductsOfficeByFranchise(Integer franchiseId);
}
