package com.lodgitechpro.hotelmanagement.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ROLE_FORM")
public class RoleForm extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_form_sequence")
    @SequenceGenerator(name = "role_form_sequence", sequenceName = "role_form_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @Column(name = "active")
    private boolean active;

    @Column(name = "description")
    private String description;

}
