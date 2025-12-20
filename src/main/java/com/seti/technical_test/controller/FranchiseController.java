package com.seti.technical_test.controller;

import com.seti.technical_test.dto.FranchiseDto;
import com.seti.technical_test.service.IFranchiseService;
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
    private final IFranchiseService iFranchiseService;

    /**
     * Retrieves a franchise by its identifier.
     *
     * @param id the unique identifier of the franchise
     * @return a Mono emitting the FranchiseDto if found, or an error if not
     */
    @GetMapping("/{id}")
    public Mono<FranchiseDto> getFranchise(@PathVariable Integer id) {
        return iFranchiseService.getFranchise(id);
    }

    /**
     * Creates a new franchise.
     *
     * @param franchiseDto the franchise data to be created
     * @return a Mono emitting the created FranchiseDto
     */
    @PostMapping
    public Mono<FranchiseDto> createFranchise(@RequestBody FranchiseDto franchiseDto) {
        return iFranchiseService.createFranchise(franchiseDto);
    }

    /**
     * Updates an existing franchise by its identifier.
     *
     * @param id the unique identifier of the franchise to update
     * @param franchiseDto the updated franchise data
     * @return a Mono emitting the updated FranchiseDto
     */
    @PutMapping("/{id}")
    public Mono<FranchiseDto> updateFranchise(@PathVariable Integer id, @RequestBody FranchiseDto franchiseDto) {
        return iFranchiseService.updateFranchise(id, franchiseDto);
    }
}
