package com.seti.technical_test.service.impl;

import com.seti.technical_test.dto.OfficeDto;
import com.seti.technical_test.entity.Office;
import com.seti.technical_test.exception.GeneralException;
import com.seti.technical_test.exception.NotFoundException;
import com.seti.technical_test.repository.OfficeRepository;
import com.seti.technical_test.service.IFranchiseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link OfficeService}.
 */
class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @Mock
    private IFranchiseService franchiseService;

    @InjectMocks
    private OfficeService officeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Should return an office when it exists.
     */
    @Test
    void getOffice_success() {
        Office office = Office.builder().id(1).name("Office").franchiseId(1).build();

        when(officeRepository.findById(1)).thenReturn(Mono.just(office));

        StepVerifier.create(officeService.getOffice(1))
                .assertNext(dto -> {
                    assert dto.name().equals("Office");
                })
                .verifyComplete();
    }

    /**
     * Should throw NotFoundException when office does not exist.
     */
    @Test
    void getOffice_notFound() {
        when(officeRepository.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(officeService.getOffice(1))
                .expectError(NotFoundException.class)
                .verify();
    }

    /**
     * Should create an office when franchise exists and name is unique.
     */
    @Test
    void createOffice_success() {
        OfficeDto dto = new OfficeDto(null, "Office", 1);

        when(officeRepository.existsByName("OFFICE")).thenReturn(Mono.just(false));
        when(franchiseService.getFranchise(1)).thenReturn(Mono.just(mock()));
        when(officeRepository.save(any()))
                .thenReturn(Mono.just(Office.builder().id(1).name("OFFICE").franchiseId(1).build()));

        StepVerifier.create(officeService.createOffice(dto))
                .assertNext(result -> {
                    assert result.name().equals("OFFICE");
                })
                .verifyComplete();
    }

    /**
     * Should fail when office name already exists.
     */
    @Test
    void createOffice_nameExists() {
        OfficeDto dto = new OfficeDto(null, "Office", 1);

        when(officeRepository.existsByName("OFFICE")).thenReturn(Mono.just(true));

        StepVerifier.create(officeService.createOffice(dto))
                .expectError(GeneralException.class)
                .verify();
    }
}
