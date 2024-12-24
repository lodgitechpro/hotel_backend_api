package com.lodgitechpro.hotelmanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ORGANIZATION")
public class Organization extends CreateAuditable {

        @Id
        private Long id;

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

        @Column(name = "CRTD_SESSION_ID")
        private String createdSessionId;
}
