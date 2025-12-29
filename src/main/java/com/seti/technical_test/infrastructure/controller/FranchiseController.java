package com.seti.technical_test.infrastructure.controller;

import com.seti.technical_test.domain.model.Franchise;
import com.seti.technical_test.application.port.in.FranchiseUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * REST controller responsible for handling franchise-related HTTP requests.
 * Exposes reactive endpoints for creating, retrieving and updating franchises.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/franchise")
public class FranchiseController {

    /**
     * Service layer that contains the business logic for franchises.
     */
    private final FranchiseUseCase franchiseUseCase;

    /**
     * Retrieves a franchise by its identifier.
     *
     * @param id the unique identifier of the franchise
     * @return a Mono emitting the FranchiseDto if found, or an error if not
     */
    @GetMapping("/{id}")
    public Mono<Franchise> getFranchise(@PathVariable Integer id) {
        return franchiseUseCase.getFranchise(id);
    }

    /**
     * Creates a new franchise.
     *
     * @param franchise the franchise data to be created
     * @return a Mono emitting the created FranchiseDto
     */
    @PostMapping
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return franchiseUseCase.createFranchise(franchise);
    }

    /**
     * Updates an existing franchise by its identifier.
     *
     * @param id the unique identifier of the franchise to update
     * @param franchise the updated franchise data
     * @return a Mono emitting the updated FranchiseDto
     */
    @PutMapping("/{id}")
    public Mono<Franchise> updateFranchise(@PathVariable Integer id, @RequestBody Franchise franchise) {
        return franchiseUseCase.updateFranchise(id, franchise);
    }
}
