package com.seti.technical_test.infrastructure.persistance.adapter;

import com.seti.technical_test.application.port.out.OfficeRepositoryPort;
import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.infrastructure.persistance.entity.OfficeEntity;
import com.seti.technical_test.infrastructure.persistance.repository.OfficeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Persistence adapter that implements {@link OfficeRepositoryPort}
 * using Spring Data R2DBC.
 *
 * <p>
 * This adapter is responsible for translating between domain models
 * and persistence entities, and for delegating database operations
 * to the underlying {@link OfficeRepository}.
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
public class OfficeRepositoryAdapter implements OfficeRepositoryPort {

    /**
     * Spring Data R2DBC repository used to interact with the database.
     */
    private final OfficeRepository officeRepository;

    /**
     * Retrieves an {@link Office} by its unique identifier.
     *
     * @param id the unique identifier of the office
     * @return a {@link Mono} emitting the office if found, or empty if not found
     */
    @Override
    public Mono<Office> findById(Integer id) {
        return officeRepository.findById(id)
                .map(this::toDomain);
    }

    /**
     * Checks whether an office with the given name already exists.
     *
     * @param name the office name to validate
     * @return a {@link Mono} emitting {@code true} if the office exists,
     *         {@code false} otherwise
     */
    @Override
    public Mono<Boolean> existsByName(String name) {
        return officeRepository.existsByName(name);
    }

    /**
     * Retrieves an {@link Office} by its name.
     *
     * @param name the office name
     * @return a {@link Mono} emitting the office if found, or empty if not found
     */
    @Override
    public Mono<Office> findByName(String name) {
        return officeRepository.findByName(name)
                .map(this::toDomain);
    }

    /**
     * Persists the given {@link Office} domain model.
     *
     * <p>
     * The domain model is mapped to a persistence entity before being
     * saved using the underlying Spring Data repository.
     * </p>
     *
     * @param office the office domain model to be persisted
     * @return a {@link Mono} emitting the persisted office
     */
    @Override
    public Mono<Office> save(Office office) {
        OfficeEntity entity = OfficeEntity.builder()
                .id(office.id())
                .name(office.name())
                .build();

        return officeRepository.save(entity)
                .map(this::toDomain);
    }

    /**
     * Retrieves all {@link Office} instances associated with a given franchise.
     *
     * @param franchiseId the unique identifier of the franchise
     * @return a {@link Flux} emitting the offices belonging to the specified franchise
     */
    @Override
    public Flux<Office> findByFranchiseId(Integer franchiseId) {
        return officeRepository.findByFranchiseId(franchiseId)
                .map(this::toDomain);
    }

    /**
     * Maps an {@link OfficeEntity} persistence entity to an
     * {@link Office} domain model.
     *
     * @param officeEntity the office persistence entity
     * @return the mapped office domain model
     */
    private Office toDomain(OfficeEntity officeEntity) {
        return new Office(
                officeEntity.getId(),
                officeEntity.getName(),
                officeEntity.getFranchiseId()
        );
    }
}
