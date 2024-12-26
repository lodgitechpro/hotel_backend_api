package com.lodgitechpro.hotelmanagement.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ORGANIZATION")
public class Organization extends CreateAuditable {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_sequence")
        @SequenceGenerator(name = "organization_sequence", sequenceName = "organization_seq", allocationSize = 1)
        private Integer id;

        @Column(name = "DESCRIPTION")
        private String description;

        @Column(name = "TITLE")
        private String title;

        @Column(name = "EMAIL_ADDRESS")
        private String emailAddress;

        @Column(name = "WEBSITE")
        private String website;

        @Column(name = "ACTIVE")
        private boolean active;

        @Column(name = "SLOGAN")
        private String slogan;

        @Column(name = "APP_SERVER")
        private String appServer;

        @Column(name = "TAX_ID")
        private String taxId;

        @Column(name = "CORPORATE_CONTACT")
        private String corporateContact;

        @Column(name = "INDUSTRY_TYPE")
        private String industryType;
}
