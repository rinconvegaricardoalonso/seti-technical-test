package com.seti.technical_test.controller;

import com.seti.technical_test.dto.ProductDto;
import com.seti.technical_test.service.IProductService;
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
    private final IProductService iProductService;

    /**
     * Retrieves a product by its identifier.
     *
     * @param id the unique identifier of the product
     * @return a Mono emitting the ProductDto if found, or an error if not
     */
    @GetMapping("/{id}")
    Mono<ProductDto> getProduct(@PathVariable Integer id) {
        return iProductService.getProduct(id);
    }

    /**
     * Creates a new product.
     *
     * @param productDto the product data to be created
     * @return a Mono emitting the created ProductDto
     */
    @PostMapping
    Mono<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return iProductService.createProduct(productDto);
    }

    /**
     * Updates an existing product by its identifier.
     *
     * @param id the unique identifier of the product to update
     * @param productDto the updated product data
     * @return a Mono emitting the updated ProductDto
     */
    @PutMapping("/{id}")
    Mono<ProductDto> updateProduct(@PathVariable Integer id, @RequestBody ProductDto productDto) {
        return iProductService.updateProduct(id, productDto);
    }

    /**
     * Deletes a product by its identifier.
     *
     * @param id the unique identifier of the product to delete
     * @return a Mono that completes when the product is deleted
     */
    @DeleteMapping("/{id}")
    Mono<Void> deleteProduct(@PathVariable Integer id) {
        return iProductService.deleteProduct(id);
    }

    /**
     * Retrieves the products with the highest stock per office
     * for a specific franchise.
     *
     * @param franchiseId the unique identifier of the franchise
     * @return a Flux emitting the top-stock ProductDto per office
     */
    @GetMapping("/top-products/{franchiseId}")
    Flux<ProductDto> getTopProductsOfficeByFranchise(@PathVariable Integer franchiseId) {
        return iProductService.getTopProductsOfficeByFranchise(franchiseId);
    }
}
