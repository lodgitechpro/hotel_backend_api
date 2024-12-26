package com.lodgitechpro.hotelmanagement.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "FORM")
public class Form extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_sequence")
    @SequenceGenerator(name = "form_sequence", sequenceName = "form_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "form_name", nullable = false)
    private String formName;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "active")
    private boolean active;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "web_url", length = 500)
    private String webUrl;

    @Column(name = "web_enabled")
    private boolean webEnabled;

    @Column(name = "desktop_enabled")
    private boolean desktopEnabled;

}
