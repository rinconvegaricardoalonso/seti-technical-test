package com.seti.technical_test.service.impl;

import com.seti.technical_test.dto.FranchiseDto;
import com.seti.technical_test.dto.OfficeDto;
import com.seti.technical_test.entity.Franchise;
import com.seti.technical_test.entity.Office;
import com.seti.technical_test.exception.GeneralException;
import com.seti.technical_test.exception.NotFoundException;
import com.seti.technical_test.repository.FranchiseRepository;
import com.seti.technical_test.repository.OfficeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link FranchiseService}.
 */
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private FranchiseService franchiseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Should return a franchise with its offices when it exists.
     */
    @Test
    void getFranchise_success() {
        Franchise franchise = Franchise.builder().id(1).name("TEST").build();

        when(franchiseRepository.findById(1)).thenReturn(Mono.just(franchise));
        when(officeRepository.findByFranchiseId(1))
                .thenReturn(Flux.just(
                        Office.builder().id(1).name("Office").franchiseId(1).build()
                ));

        StepVerifier.create(franchiseService.getFranchise(1))
                .assertNext(dto -> {
                    assert dto.id().equals(1);
                    assert dto.offices().size() == 1;
                })
                .verifyComplete();
    }

    /**
     * Should throw NotFoundException when franchise does not exist.
     */
    @Test
    void getFranchise_notFound() {
        when(franchiseRepository.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.getFranchise(1))
                .expectError(NotFoundException.class)
                .verify();
    }

    /**
     * Should create a franchise when name does not exist.
     */
    @Test
    void createFranchise_success() {
        FranchiseDto dto = new FranchiseDto(null, "test", null);

        when(franchiseRepository.existsByName("TEST")).thenReturn(Mono.just(false));
        when(franchiseRepository.save(any()))
                .thenReturn(Mono.just(Franchise.builder().id(1).name("TEST").build()));

        StepVerifier.create(franchiseService.createFranchise(dto))
                .assertNext(result -> {
                    assert result.name().equals("TEST");
                })
                .verifyComplete();
    }

    /**
     * Should fail when franchise name already exists.
     */
    @Test
    void createFranchise_nameExists() {
        FranchiseDto dto = new FranchiseDto(null, "test", null);

        when(franchiseRepository.existsByName("TEST")).thenReturn(Mono.just(true));

        StepVerifier.create(franchiseService.createFranchise(dto))
                .expectError(GeneralException.class)
                .verify();
    }
}
