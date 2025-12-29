package com.seti.technical_test.infrastructure.persistance.adapter;

import com.seti.technical_test.application.port.out.FranchiseRepositoryPort;
import com.seti.technical_test.domain.model.Franchise;
import com.seti.technical_test.infrastructure.persistance.entity.FranchiseEntity;
import com.seti.technical_test.infrastructure.persistance.repository.FranchiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Persistence adapter that implements {@link FranchiseRepositoryPort}
 * using Spring Data R2DBC.
 *
 * <p>
 * This adapter acts as a bridge between the application layer and the
 * persistence infrastructure. It translates domain models into
 * persistence entities and delegates database operations to the
 * {@link FranchiseRepository}.
 * </p>
 *
 * <p>
 * By implementing the output port, this class allows the application
 * and domain layers to remain decoupled from Spring Data and R2DBC.
 * </p>
 */
@Repository
@AllArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    /**
     * Spring Data R2DBC repository used to interact with the database.
     */
    private final FranchiseRepository franchiseRepository;

    /**
     * Retrieves a franchise by its unique identifier.
     *
     * @param id the unique identifier of the franchise
     * @return a {@link Mono} emitting the franchise if found, or empty if not found
     */
    @Override
    public Mono<Franchise> findById(Integer id) {
        return franchiseRepository.findById(id)
                .map(this::toDomain);
    }

    /**
     * Checks whether a franchise with the given name already exists.
     *
     * @param name the franchise name to validate
     * @return a {@link Mono} emitting {@code true} if the franchise exists,
     *         {@code false} otherwise
     */
    @Override
    public Mono<Boolean> existsByName(String name) {
        return franchiseRepository.existsByName(name);
    }

    /**
     * Retrieves a franchise by its name.
     *
     * @param name the franchise name
     * @return a {@link Mono} emitting the franchise if found, or empty if not found
     */
    @Override
    public Mono<Franchise> findByName(String name) {
        return franchiseRepository.findByName(name)
                .map(this::toDomain);
    }

    /**
     * Persists the given {@link Franchise} domain model.
     *
     * <p>
     * The domain model is first mapped to a persistence entity before
     * being saved using the underlying Spring Data repository.
     * </p>
     *
     * @param franchise the franchise domain model to be persisted
     * @return a {@link Mono} emitting the persisted franchise
     */
    @Override
    public Mono<Franchise> save(Franchise franchise) {
        FranchiseEntity entity = FranchiseEntity.builder()
                .id(franchise.id())
                .name(franchise.name())
                .build();

        return franchiseRepository.save(entity)
                .map(this::toDomain);
    }

    /**
     * Maps a {@link FranchiseEntity} persistence entity to a
     * {@link Franchise} domain model.
     *
     * @param franchiseEntity the franchise persistence entity
     * @return the mapped franchise domain model
     */
    private Franchise toDomain(FranchiseEntity franchiseEntity) {
        return new Franchise(
                franchiseEntity.getId(),
                franchiseEntity.getName(),
                null
        );
    }
}
