package com.seti.technical_test.application.service;

import com.seti.technical_test.application.port.out.OfficeRepositoryPort;
import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.infrastructure.exception.GeneralException;
import com.seti.technical_test.infrastructure.exception.NotFoundException;
import com.seti.technical_test.application.port.in.FranchiseUseCase;
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
    private OfficeRepositoryPort officeRepositoryPort;

    @Mock
    private FranchiseUseCase franchiseUseCase;

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
        Office office = new Office(1, "Office", 1);

        when(officeRepositoryPort.findById(1)).thenReturn(Mono.just(office));

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
        when(officeRepositoryPort.findById(1)).thenReturn(Mono.empty());

        StepVerifier.create(officeService.getOffice(1))
                .expectError(NotFoundException.class)
                .verify();
    }

    /**
     * Should create an office when franchise exists and name is unique.
     */
    @Test
    void createOffice_success() {
        Office dto = new Office(null, "Office", 1);

        when(officeRepositoryPort.existsByName("OFFICE")).thenReturn(Mono.just(false));
        when(franchiseUseCase.getFranchise(1)).thenReturn(Mono.just(mock()));
        when(officeRepositoryPort.save(any()))
                .thenReturn(Mono.just(new Office(null, "OFFICE", 1)));

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
        Office dto = new Office(null, "Office", 1);

        when(officeRepositoryPort.existsByName("OFFICE")).thenReturn(Mono.just(true));

        StepVerifier.create(officeService.createOffice(dto))
                .expectError(GeneralException.class)
                .verify();
    }
}
