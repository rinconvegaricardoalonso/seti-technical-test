package com.seti.technical_test.infrastructure.controller;

import com.seti.technical_test.domain.model.Office;
import com.seti.technical_test.application.port.in.OfficeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller responsible for handling office-related HTTP requests.
 * Provides reactive endpoints for creating, retrieving and updating offices.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/office")
public class OfficeController {

    /**
     * Service layer that contains the business logic for offices.
     */
    private final OfficeUseCase officeUseCase;

    /**
     * Retrieves an office by its identifier.
     *
     * @param id the unique identifier of the office
     * @return a Mono emitting the OfficeDto if found, or an error if not
     */
    @GetMapping("/{id}")
    Mono<Office> getOffice(@PathVariable Integer id) {
        return officeUseCase.getOffice(id);
    }

    /**
     * Creates a new office.
     *
     * @param office the office data to be created
     * @return a Mono emitting the created OfficeDto
     */
    @PostMapping
    Mono<Office> createOffice(@RequestBody Office office) {
        return officeUseCase.createOffice(office);
    }

    /**
     * Updates an existing office by its identifier.
     *
     * @param id the unique identifier of the office to update
     * @param office the updated office data
     * @return a Mono emitting the updated OfficeDto
     */
    @PutMapping("/{id}")
    Mono<Office> updateOffice(@PathVariable Integer id, @RequestBody Office office) {
        return officeUseCase.updateOffice(id, office);
    }


}
