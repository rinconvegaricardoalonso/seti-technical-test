package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.out.FranchiseRepositoryPort;
import com.seti.technical_test.application.port.out.OfficeRepositoryPort;
import com.seti.technical_test.domain.model.Franchise;
import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link FranchiseService}.
 */
class FranchiseServiceTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @Mock
    private OfficeRepositoryPort officeRepositoryPort;

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
        Franchise franchise = new Franchise(1, "TEST", null);

        when(franchiseRepositoryPort.findById(1)).thenReturn(Mono.just(franchise));
        when(officeRepositoryPort.findByFranchiseId(1))
                .thenReturn(Flux.just(
                        new Office(1, "Office", 1)
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
        when(franchiseRepositoryPort.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(franchiseService.getFranchise(1))
                .expectError(NotFoundException.class)
                .verify();
    }

    /**
     * Should create a franchise when name does not exist.
     */
    @Test
    void createFranchise_success() {
        Franchise dto = new Franchise(null, "TEST", null);

        when(franchiseRepositoryPort.existsByName("TEST")).thenReturn(Mono.just(false));
        when(franchiseRepositoryPort.save(any()))
                .thenReturn(Mono.just(dto));

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
        Franchise dto = new Franchise(null, "test", null);

        when(franchiseRepositoryPort.existsByName("TEST")).thenReturn(Mono.just(true));

        StepVerifier.create(franchiseService.createFranchise(dto))
                .expectError(GeneralException.class)
                .verify();
    }
}
