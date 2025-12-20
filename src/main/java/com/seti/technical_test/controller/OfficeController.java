package com.seti.technical_test.controller;

import com.seti.technical_test.dto.OfficeDto;
import com.seti.technical_test.service.IOfficeService;
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
    private final IOfficeService iOfficeService;

    /**
     * Retrieves an office by its identifier.
     *
     * @param id the unique identifier of the office
     * @return a Mono emitting the OfficeDto if found, or an error if not
     */
    @GetMapping("/{id}")
    Mono<OfficeDto> getOffice(@PathVariable Integer id) {
        return iOfficeService.getOffice(id);
    }

    /**
     * Creates a new office.
     *
     * @param officeDto the office data to be created
     * @return a Mono emitting the created OfficeDto
     */
    @PostMapping
    Mono<OfficeDto> createOffice(@RequestBody OfficeDto officeDto) {
        return iOfficeService.createOffice(officeDto);
    }

    /**
     * Updates an existing office by its identifier.
     *
     * @param id the unique identifier of the office to update
     * @param officeDto the updated office data
     * @return a Mono emitting the updated OfficeDto
     */
    @PutMapping("/{id}")
    Mono<OfficeDto> updateOffice(@PathVariable Integer id, @RequestBody OfficeDto officeDto) {
        return iOfficeService.updateOffice(id, officeDto);
    }


}
