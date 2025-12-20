package com.seti.technical_test.service.impl;

import com.seti.technical_test.dto.FranchiseDto;
import com.seti.technical_test.dto.OfficeDto;
import com.seti.technical_test.entity.Franchise;
import com.seti.technical_test.exception.GeneralException;
import com.seti.technical_test.exception.NotFoundException;
import com.seti.technical_test.repository.FranchiseRepository;
import com.seti.technical_test.repository.OfficeRepository;
import com.seti.technical_test.service.IFranchiseService;
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
public class FranchiseService implements IFranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final OfficeRepository officeRepository;

    @Override
    public Mono<FranchiseDto> getFranchise(Integer id) {
        log.info("Franchise will be consulted by id {}", id);

        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found franchise")))
                .flatMap(franchise ->
                        findByFranchiseId(id)
                                .collectList()
                                .map(offices -> toDto(franchise, offices))
                        );
    }

    @Override
    public Mono<FranchiseDto> createFranchise(FranchiseDto franchiseDto) {
        log.info("Creating franchise with the following features {}", franchiseDto);

        return franchiseRepository.existsByName(franchiseDto.name().toUpperCase().trim())
                .flatMap(exists -> {
                    if(exists) {
                        return Mono.error(new GeneralException("The franchise with the name [" + franchiseDto.name() + "] already exists"));
                    }

                    return franchiseRepository.save(Franchise.builder().name(franchiseDto.name().toUpperCase().trim()).build())
                            .map(franchise -> toDto(franchise, null));
                });
    }

    @Override
    public Mono<FranchiseDto> updateFranchise(Integer id, FranchiseDto franchiseDto) {
        log.info("Updating franchise with the following features {}", franchiseDto);

        if(!Objects.equals(id, franchiseDto.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        return getFranchise(id) // check if franchise exists
                .then(franchiseRepository.findByName(franchiseDto.name().toUpperCase().trim())
                        .flatMap(franchise -> {
                            if(!Objects.equals(franchise.getId(), franchiseDto.id())) { // It is validated that the current database franchise will be updated
                                return Mono.error(new GeneralException("You are trying to update the franchise name to an existing one"));
                            }

                            return franchiseRepository.save(Franchise.builder().id(franchiseDto.id()).name(franchiseDto.name().toUpperCase().trim()).build())
                                    .map(franchiseSaved -> toDto(franchiseSaved, null));
                        }));
    }

    @Override
    public Flux<OfficeDto> findByFranchiseId(Integer franchiseId) {
        log.info("Checking offices for the franchise {}", franchiseId);

        return officeRepository.findByFranchiseId(franchiseId)
                .map(office -> new OfficeDto(office.getId(), office.getName(), office.getFranchiseId()));
    }

    /**
     * Maps a {@link Franchise} entity to a {@link FranchiseDto}.
     *
     * @param franchise the franchise entity
     * @param offices the associated offices
     * @return the franchise DTO
     */
    private FranchiseDto toDto(Franchise franchise, List<OfficeDto> offices) {
        return  new FranchiseDto(franchise.getId(), franchise.getName(), offices);
    }
}
