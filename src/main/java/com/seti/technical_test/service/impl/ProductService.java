package com.seti.technical_test.service.impl;

import com.seti.technical_test.dto.ProductDto;
import com.seti.technical_test.entity.Product;
import com.seti.technical_test.exception.GeneralException;
import com.seti.technical_test.exception.NotFoundException;
import com.seti.technical_test.repository.ProductRepository;
import com.seti.technical_test.service.IOfficeService;
import com.seti.technical_test.service.IProductService;
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
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final IOfficeService iOfficeService;

    @Override
    public Mono<ProductDto> getProduct(Integer id) {
        log.info("Product will be consulted by id {}", id);

        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found Product")))
                .map(this::toDto);
    }

    @Override
    public Mono<ProductDto> createProduct(ProductDto productDto) {
        log.info("Creating product with the following features {}", productDto);

        return productRepository.existsByName(productDto.name().toUpperCase().trim())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new GeneralException("The product with the name [" + productDto.name() + "] already exists"));
                    }

                    return iOfficeService.getOffice(productDto.officeId())
                            .then(productRepository.save(Product.builder().name(productDto.name().toUpperCase().trim()).stock(productDto.stock()).officeId(productDto.officeId()).build())
                                    .map(this::toDto));
                });
    }

    @Override
    public Mono<ProductDto> updateProduct(Integer id, ProductDto productDto) {
        if(!Objects.equals(id, productDto.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        log.info("Updating product with the following features {}", productDto);

        return getProduct(id) // check if product exists
                .then(productRepository.findByName(productDto.name().toUpperCase().trim()) // the product is searched by name.
                        .flatMap(product -> {
                            if(!Objects.equals(product.getId(), productDto.id())) { // It is validated that the current database product will be updated
                                return Mono.error(new GeneralException("You are trying to update the product name to an existing one"));
                            }

                            return productRepository.save(Product.builder().id(productDto.id()).name(productDto.name().toUpperCase().trim()).stock(productDto.stock()).officeId(productDto.officeId()).build())
                                    .map(this::toDto);
                        }));
    }

    @Override
    public Mono<Void> deleteProduct(Integer id) {
        log.info("Deleting product by id {}", id);

        return getProduct(id)// check if product exists
                .flatMap(productDto -> productRepository.delete(Product.builder().name(productDto.name()).stock(productDto.stock()).officeId(productDto.officeId()).build()));
    }

    @Override
    public Flux<ProductDto> getTopProductsOfficeByFranchise(Integer franchiseId) {
        log.info("Checking products with more stock for the franchise {}", franchiseId);

        return productRepository.findTopStockByOffice(franchiseId)
                .map(this::toDto);
    }

    /**
     * Maps a {@link Product} entity to a {@link ProductDto}.
     *
     * @param product the product entity
     * @return the product DTO
     */
    private ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getStock(), product.getOfficeId());
    }
}
