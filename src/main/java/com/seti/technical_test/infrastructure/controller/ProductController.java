package com.seti.technical_test.infrastructure.controller;

import com.seti.technical_test.domain.model.Product;
import com.seti.technical_test.application.port.in.ProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST controller responsible for handling product-related HTTP requests.
 * Exposes reactive endpoints for managing products and querying stock information.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    /**
     * Service layer that contains the business logic for products.
     */
    private final ProductUseCase productUseCase;

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the unique identifier of the product
     * @return a Mono emitting the ProductDto if found, or an error if not
     */
    @GetMapping("/{id}")
    Mono<Product> getProduct(@PathVariable Integer id) {
        return productUseCase.getProduct(id);
    }

    /**
     * Creates a new product.
     *
     * @param product the product data to be created
     * @return a Mono emitting the created ProductDto
     */
    @PostMapping
    Mono<Product> createProduct(@RequestBody Product product) {
        return productUseCase.createProduct(product);
    }

    /**
     * Updates an existing product by its identifier.
     *
     * @param id the unique identifier of the product to update
     * @param product the updated product data
     * @return a Mono emitting the updated ProductDto
     */
    @PutMapping("/{id}")
    Mono<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productUseCase.updateProduct(id, product);
    }

    /**
     * Deletes a product by its identifier.
     *
     * @param id the unique identifier of the product to delete
     * @return a Mono that completes when the product is deleted
     */
    @DeleteMapping("/{id}")
    Mono<Void> deleteProduct(@PathVariable Integer id) {
        return productUseCase.deleteProduct(id);
    }

    /**
     * Retrieves the products with the highest stock per office
     * for a specific franchise.
     *
     * @param franchiseId the unique identifier of the franchise
     * @return a Flux emitting the top-stock ProductDto per office
     */
    @GetMapping("/top-products/{franchiseId}")
    Flux<Product> getTopProductsOfficeByFranchise(@PathVariable Integer franchiseId) {
        return productUseCase.getTopProductsOfficeByFranchise(franchiseId);
    }
}
