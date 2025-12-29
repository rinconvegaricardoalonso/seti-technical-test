package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.in.FranchiseUseCase;
import com.seti.technical_test.application.port.out.FranchiseRepositoryPort;
import com.seti.technical_test.application.port.out.OfficeRepositoryPort;
import com.seti.technical_test.domain.model.Franchise;
import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.infrastructure.persistance.entity.FranchiseEntity;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

/**
 * Service implementation for managing franchises.
 * <p>
 * This class contains the business logic related to franchises,
 * using reactive programming with Project Reactor.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FranchiseService implements FranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;
    private final OfficeRepositoryPort officeRepositoryPort;

    @Override
    public Mono<Franchise> getFranchise(Integer id) {
        log.info("Franchise will be consulted by id {}", id);

        return franchiseRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found franchise")))
                .flatMap(franchise ->
                        findByFranchiseId(id)
                                .collectList()
                                .map(offices -> addOfficesToFranchise(franchise, offices))
                        );
    }

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        log.info("Creating franchise with the following features {}", franchise);

        return franchiseRepositoryPort.existsByName(franchise.name())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new GeneralException("The franchise with the name [" + franchise.name() + "] already exists"));
                    }

                    return franchiseRepositoryPort.save(franchise);
                });
    }

    @Override
    public Mono<Franchise> updateFranchise(Integer id, Franchise franchise) {
        log.info("Updating franchise with the following features {}", franchise);

        if(!Objects.equals(id, franchise.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        return getFranchise(id) // check if franchise exists
                .then(franchiseRepositoryPort.findByName(franchise.name())
                        .flatMap(consultedFranchise -> {
                            if(!Objects.equals(consultedFranchise.id(), franchise.id())) { // It is validated that the current database consultedFranchise will be updated
                                return Mono.error(new GeneralException("You are trying to update the consultedFranchise name to an existing one"));
                            }

                            return franchiseRepositoryPort.save(franchise);
                        }));
    }

    @Override
    public Flux<Office> findByFranchiseId(Integer franchiseId) {
        log.info("Checking offices for the franchise {}", franchiseId);

        return officeRepositoryPort.findByFranchiseId(franchiseId);
    }

    /**
     * Maps a {@link FranchiseEntity} entity to a {@link Franchise}.
     *
     * @param franchise the franchiseDto entity
     * @param offices the associated offices
     * @return the franchiseDto DTO
     */
    private Franchise addOfficesToFranchise(Franchise franchise, List<Office> offices) {
        return new Franchise(franchise.id(), franchise.name(), offices);
    }
}
