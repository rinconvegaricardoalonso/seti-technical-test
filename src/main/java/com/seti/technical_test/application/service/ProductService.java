package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.out.ProductRepositoryPort;
import com.seti.technical_test.domain.model.Product;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import com.seti.technical_test.application.port.in.OfficeUseCase;
import com.seti.technical_test.application.port.in.ProductUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Service implementation for managing products.
 * <p>
 * Contains the business logic related to product operations,
 * implemented using reactive programming.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final OfficeUseCase officeUseCase;

    @Override
    public Mono<Product> getProduct(Integer id) {
        log.info("Product will be consulted by id {}", id);

        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found Product")));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        log.info("Creating product with the following features {}", product);

        return productRepositoryPort.existsByName(product.name())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new GeneralException("The product with the name [" + product.name() + "] already exists"));
                    }

                    return officeUseCase.getOffice(product.officeId())
                            .then(productRepositoryPort.save(product));
                });
    }

    @Override
    public Mono<Product> updateProduct(Integer id, Product product) {
        if(!Objects.equals(id, product.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        log.info("Updating product with the following features {}", product);

        return getProduct(id) // check if product exists
                .then(productRepositoryPort.findByName(product.name()) // the product is searched by name.
                        .flatMap(consultedProduct -> {
                            if(!Objects.equals(consultedProduct.id(), product.id())) { // It is validated that the current database consultedProduct will be updated
                                return Mono.error(new GeneralException("You are trying to update the consultedProduct name to an existing one"));
                            }

                            return productRepositoryPort.save(product);
                        }));
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        log.info("Deleting product by id {}", id);

        return getProduct(id)// check if product exists
                .flatMap(productRepositoryPort::delete);
    }

    @Override
    public Flux<Product> getTopProductsOfficeByFranchise(Integer franchiseId) {
        log.info("Checking products with more stock for the franchise {}", franchiseId);

        return productRepositoryPort.findTopStockByOffice(franchiseId);
    }
}
