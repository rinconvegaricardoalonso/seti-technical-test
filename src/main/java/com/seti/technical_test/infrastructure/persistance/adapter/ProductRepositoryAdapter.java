package com.seti.technical_test.infrastructure.persistance.adapter;

import com.seti.technical_test.application.port.out.ProductRepositoryPort;
import com.seti.technical_test.domain.model.Product;
import com.seti.technical_test.infrastructure.persistance.entity.ProductEntity;
import com.seti.technical_test.infrastructure.persistance.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Persistence adapter that implements {@link ProductRepositoryPort}
 * using Spring Data R2DBC.
 *
 * <p>
 * This adapter is responsible for translating between domain models
 * and persistence entities and for delegating database operations
 * to the underlying {@link ProductRepository}.
 * </p>
 *
 * <p>
 * By implementing the output port, this adapter ensures that the
 * application and domain layers remain independent from the
 * persistence technology.
 * </p>
 */
@Repository
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    /**
     * Spring Data R2DBC repository used to interact with the database.
     */
    private final ProductRepository productRepository;

    /**
     * Retrieves a {@link Product} by its unique identifier.
     *
     * @param id the unique identifier of the product
     * @return a {@link Mono} emitting the product if found, or empty if not found
     */
    @Override
    public Mono<Product> findById(Integer id) {
        return productRepository.findById(id)
                .map(this::toDomain);
    }

    /**
     * Checks whether a product with the given name already exists.
     *
     * @param name the product name to validate
     * @return a {@link Mono} emitting {@code true} if the product exists,
     *         {@code false} otherwise
     */
    @Override
    public Mono<Boolean> existsByName(String name) {
        return productRepository.existsByName(name);
    }

    /**
     * Retrieves a {@link Product} by its name.
     *
     * @param name the product name
     * @return a {@link Mono} emitting the product if found, or empty if not found
     */
    @Override
    public Mono<Product> findByName(String name) {
        return productRepository.findByName(name)
                .map(this::toDomain);
    }

    /**
     * Persists the given {@link Product} domain model.
     *
     * <p>
     * The domain model is mapped to a persistence entity before being
     * saved using the underlying Spring Data repository.
     * </p>
     *
     * @param product the product domain model to be persisted
     * @return a {@link Mono} emitting the persisted product
     */
    @Override
    public Mono<Product> save(Product product) {
        ProductEntity entity = ProductEntity.builder()
                .id(product.id())
                .name(product.name())
                .stock(product.stock())
                .officeId(product.officeId())
                .build();

        return productRepository.save(entity)
                .map(this::toDomain);
    }

    /**
     * Deletes the given {@link Product} domain model.
     *
     * <p>
     * The domain model is mapped to a persistence entity before being
     * deleted using the underlying Spring Data repository.
     * </p>
     *
     * @param product the product domain model to be deleted
     * @return a {@link Mono} that completes when the deletion is finished
     */
    @Override
    public Mono<Void> delete(Product product) {
        ProductEntity entity = ProductEntity.builder()
                .id(product.id())
                .name(product.name())
                .stock(product.stock())
                .officeId(product.officeId())
                .build();

        return productRepository.delete(entity);
    }

    /**
     * Retrieves the products with the highest stock for a given office or franchise.
     *
     * @param franchiseId the unique identifier of the franchise or office
     * @return a {@link Flux} emitting the products with the highest stock
     */
    @Override
    public Flux<Product> findTopStockByOffice(Integer franchiseId) {
        return productRepository.findTopStockByOffice(franchiseId)
                .map(this::toDomain);
    }

    /**
     * Maps a {@link ProductEntity} persistence entity to a
     * {@link Product} domain model.
     *
     * @param productEntity the product persistence entity
     * @return the mapped product domain model
     */
    private Product toDomain(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getStock(),
                productEntity.getOfficeId()
        );
    }
}
