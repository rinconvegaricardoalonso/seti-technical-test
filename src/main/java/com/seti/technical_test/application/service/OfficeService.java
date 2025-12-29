package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.in.OfficeUseCase;
import com.seti.technical_test.application.port.out.OfficeRepositoryPort;
import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import com.seti.technical_test.application.port.in.FranchiseUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Service implementation for managing offices.
 * <p>
 * Contains the business logic related to office operations,
 * implemented using reactive programming.
 */
@Service
@AllArgsConstructor
@Slf4j
public class OfficeService implements OfficeUseCase {

    private final OfficeRepositoryPort officeRepositoryPort;
    private final FranchiseUseCase franchiseUseCase;

    @Override
    public Mono<Office> getOffice(Integer id) {
        log.info("Office will be consulted by id {}", id);

        return officeRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found office")));
    }

    @Override
    public Mono<Office> createOffice(Office office) {
        log.info("Creating office with the following features {}", office);

        return officeRepositoryPort.existsByName(office.name())
                    .flatMap(exists -> {
                        if(exists) {
                            return Mono.error(new GeneralException("The office with the name [" + office.name() + "] already exists"));
                        }

                        return franchiseUseCase.getFranchise(office.franchiseId()) // check if franchise exists
                                .then(officeRepositoryPort.save(office));
                    });

    }

    @Override
    public Mono<Office> updateOffice(Integer id, Office office) {
        if(!Objects.equals(id, office.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        log.info("Updating office with the following features {}", office);

        return getOffice(id) // check if office exists
                .then(officeRepositoryPort.findByName(office.name())
                        .flatMap(consultedOffice -> {
                            if(!Objects.equals(consultedOffice.id(), office.id())) { // It is validated that the current database consultedOffice will be updated
                                return Mono.error(new GeneralException("You are trying to update the consultedOffice name to an existing one"));
                            }

                            return officeRepositoryPort.save(office);
                        }));
    }
}
