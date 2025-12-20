package com.seti.technical_test.service;

import com.seti.technical_test.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing products.
 * <p>
 * Defines the contract for product-related business operations
 * using reactive programming.
 */
public interface IProductService {

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the product identifier
     * @return a {@link Mono} containing the product data
     */
    Mono<ProductDto> getProduct(Integer id);

    /**
     * Creates a new product.
     *
     * @param productDto the product data to be created
     * @return a {@link Mono} containing the created product
     */
    Mono<ProductDto> createProduct(ProductDto productDto);

    /**
     * Updates an existing product.
     *
     * @param id the product identifier
     * @param productDto the updated product data
     * @return a {@link Mono} containing the updated product
     */
    Mono<ProductDto> updateProduct(Integer id, ProductDto productDto);

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
    Flux<ProductDto> getTopProductsOfficeByFranchise(Integer franchiseId);
}
