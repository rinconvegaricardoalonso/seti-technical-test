package com.seti.technical_test.service.impl;

import com.seti.technical_test.dto.OfficeDto;
import com.seti.technical_test.entity.Office;
import com.seti.technical_test.exception.GeneralException;
import com.seti.technical_test.exception.NotFoundException;
import com.seti.technical_test.repository.OfficeRepository;
import com.seti.technical_test.service.IFranchiseService;
import com.seti.technical_test.service.IOfficeService;
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
public class OfficeService implements IOfficeService {

    private final OfficeRepository officeRepository;
    private final IFranchiseService iFranchiseService;

    @Override
    public Mono<OfficeDto> getOffice(Integer id) {
        log.info("Office will be consulted by id {}", id);

        return officeRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Not found office")))
                .map(this::toDto);
    }

    @Override
    public Mono<OfficeDto> createOffice(OfficeDto officeDto) {
        log.info("Creating office with the following features {}", officeDto);

        return officeRepository.existsByName(officeDto.name().toUpperCase().trim())
                    .flatMap(exists -> {
                        if(exists) {
                            return Mono.error(new GeneralException("The office with the name [" + officeDto.name() + "] already exists"));
                        }

                        return iFranchiseService.getFranchise(officeDto.franchiseId()) // check if franchise exists
                                .then(officeRepository.save(Office.builder().name(officeDto.name().toUpperCase().trim()).franchiseId(officeDto.franchiseId()).build())
                                        .map(this::toDto));
                    });

    }

    @Override
    public Mono<OfficeDto> updateOffice(Integer id, OfficeDto officeDto) {
        if(!Objects.equals(id, officeDto.id())) {
            log.error("IDs do not match");
            throw new GeneralException("IDs do not match");
        }

        log.info("Updating office with the following features {}", officeDto);

        return getOffice(id) // check if office exists
                .then(officeRepository.findByName(officeDto.name().toUpperCase().trim())
                        .flatMap(office -> {
                            if(!Objects.equals(office.getId(), officeDto.id())) { // It is validated that the current database office will be updated
                                return Mono.error(new GeneralException("You are trying to update the office name to an existing one"));
                            }

                            return officeRepository.save(Office.builder().id(officeDto.id()).name(officeDto.name().toUpperCase().trim()).build())
                                    .map(this::toDto);
                        }));
    }

    /**
     * Maps an {@link Office} entity to an {@link OfficeDto}.
     *
     * @param office the office entity
     * @return the office DTO
     */
    private OfficeDto toDto(Office office) {
        return new OfficeDto(office.getId(), office.getName(), office.getFranchiseId());
    }
}
