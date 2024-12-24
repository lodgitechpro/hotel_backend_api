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
@Table(name = "LOCATION")
public class Location extends CreateAuditable {

        @Id
        private Long id;

        @Column(name = "DESCRIPTION")
        private String description;

        @Column(name = "ACTIVE")
        private boolean active;

        @Column(name = "ORG_ID")
        private Long orgId;

        @Column(name = "ADDRESS")
        private String address;

        @Column(name = "CONTACT_INFO")
        private String contactInfo;

        @Column(name = "LEFT_LOGO")
        private String leftLogo;

        @Column(name = "RIGHT_LOGO")
        private String rightLogo;

        @Column(name = "CRTD_SESSION_ID")
        private String createdSessionId;
}
