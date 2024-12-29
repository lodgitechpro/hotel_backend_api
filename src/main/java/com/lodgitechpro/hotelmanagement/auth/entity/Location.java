package com.lodgitechpro.hotelmanagement.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "LOCATION")
public class Location extends CreateAuditable {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence")
        @SequenceGenerator(name = "location_sequence", sequenceName = "location_seq", allocationSize = 1)
        private Integer id;

        @Column(name = "DESCRIPTION")
        private String description;

        @Column(name = "ACTIVE")
        private boolean active;

        @Column(name = "ORG_ID")
        private Integer orgId;

        @Column(name = "STREET_ADDRESS")
        private String streetAddress;

        @Column(name = "CITY")
        private String city;

        @Column(name = "state")
        private String state;

        @Column(name = "ZIP_CODE")
        private Integer zipCode;

        @Column(name = "COUNTRY")
        private String country;

        @Column(name = "LATITUDE")
        private String latitude;

        @Column(name = "LONGITUDE")
        private String longitude;

        @Column(name = "CONTACT_INFO")
        private String contactInfo;

        @Column(name = "LEFT_LOGO")
        private String leftLogo;

        @Column(name = "RIGHT_LOGO")
        private String rightLogo;

        @Column(name = "PARENT_LOCATION_ID")
        private Integer parentLocationId;
}
