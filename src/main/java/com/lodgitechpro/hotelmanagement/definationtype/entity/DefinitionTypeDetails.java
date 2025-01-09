package com.lodgitechpro.hotelmanagement.definationtype.entity;

import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "DEFINITION_TYPE_DETAILS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"DEFINITION_TYPE_ID", "NAME"})
})
public class DefinitionTypeDetails extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "definition_type_details_sequence")
    @SequenceGenerator(name = "definition_type_details_sequence", sequenceName = "definition_type_details_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEFINITION_TYPE_ID", nullable = false)
    private DefinitionTypes definitionType;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;
}
