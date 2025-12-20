package com.seti.technical_test.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity representing an office.
 * Maps to the "office" table in the database.
 */
@Builder
@Data
@Table("office")
public class Office {

    /**
     * Primary key of the office.
     */
    @Id
    private Integer id;

    /**
     * Name of the office.
     */
    private String name;

    /**
     * Identifier of the franchise to which this office belongs.
     * Acts as a foreign key reference to the franchise table.
     */
    private Integer franchiseId;
}
