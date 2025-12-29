package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.out.ProductRepositoryPort;
import com.seti.technical_test.domain.model.Product;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link ProductService}.
 */
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Should return a product when it exists.
     */
    @Test
    void getProduct_success() {
        Product product = new Product(1, "Product", 10, 1);

        when(productRepositoryPort.findById(1)).thenReturn(Mono.just(product));

        StepVerifier.create(productService.getProduct(1))
                .assertNext(dto -> {
                    assert dto.id().equals(1);
                    assert dto.name().equals("Product");
                    assert dto.stock().equals(10);
                    assert dto.officeId().equals(1);
                })
                .verifyComplete();
    }


    /**
     * Should throw NotFoundException when product does not exist.
     */
    @Test
    void getProduct_notFound() {
        when(productRepositoryPort.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(productService.getProduct(1))
                .expectError(NotFoundException.class)
                .verify();
    }

    /**
     * Should return top products per office by franchise.
     */
    @Test
    void getTopProductsOfficeByFranchise_success() {
        when(productRepositoryPort.findTopStockByOffice(1))
                .thenReturn(Flux.just(new Product(1, "Product", 10, 1)));

        StepVerifier.create(productService.getTopProductsOfficeByFranchise(1))
                .expectNextCount(1)
                .verifyComplete();
    }

    /**
     * Should fail when product name already exists.
     */
    @Test
    void createProduct_nameExists() {
        Product dto = new Product(null, "Product", 10, 1);

        when(productRepositoryPort.existsByName("PRODUCT")).thenReturn(Mono.just(true));

        StepVerifier.create(productService.createProduct(dto))
                .expectError(GeneralException.class)
                .verify();
    }
}
