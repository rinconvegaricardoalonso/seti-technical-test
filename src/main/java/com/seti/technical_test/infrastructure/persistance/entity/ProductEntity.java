package com.seti.technical_test.infrastructure.persistance.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity representing a product.
 * Maps to the "product" table in the database.
 */
@Builder
@Data
@Table("product")
public class ProductEntity {

    /**
     * Primary key of the product.
     */
    @Id
    private Integer id;

    /**
     * Name of the product.
     */
    private String name;

    /**
     * Available stock for the product.
     */
    private Integer stock;

    /**
     * Identifier of the office to which this product belongs.
     * Acts as a foreign key reference to the office table.
     */
    private Integer officeId;
}
