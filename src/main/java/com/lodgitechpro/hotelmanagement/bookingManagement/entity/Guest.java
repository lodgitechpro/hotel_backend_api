package com.lodgitechpro.hotelmanagement.bookingManagement.entity;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.IdDocumentType;
import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "GUESTS")
public class Guest extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guests_sequence")
    @SequenceGenerator(name = "guests_sequence", sequenceName = "guests_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ID_DOCUMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private IdDocumentType idDocumentType;

    @Column(name = "ID_DOCUMENT_NUMBER")
    private String idDocumentNumber;

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "ZIP_CODE")
    private Integer zipCode;
}
