package com.lodgitechpro.hotelmanagement.definationtype.entity;

import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.IdDocumentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "DEFINITION_TYPES")
public class DefinitionTypes extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "definition_types_sequence")
    @SequenceGenerator(name = "definition_types_sequence", sequenceName = "definition_types_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;
}
