package com.seti.technical_test.infrastructure.persistance.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity representing a franchise.
 * Maps to the "franchise" table in the database.
 */
@Builder
@Data
@Table("franchise")
public class FranchiseEntity {

    /**
     * Primary key of the franchise.
     */
    @Id
    private Integer id;

    /**
     * Name of the franchise.
     */
    private String name;
}
