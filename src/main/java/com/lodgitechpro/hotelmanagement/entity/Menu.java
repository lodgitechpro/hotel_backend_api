package com.lodgitechpro.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "MENU")
public class Menu extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_sequence")
    @SequenceGenerator(name = "menu_sequence", sequenceName = "menu_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    private Integer position;

    @Column(name = "active")
    private boolean active;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "mnemonic")
    private String mnemonic;
}

