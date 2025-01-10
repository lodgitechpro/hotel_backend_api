package com.lodgitechpro.hotelmanagement.pricinganddiscount.entity;

import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "LOYALTY_PROGRAM")
public class LoyaltyProgram extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loyalty_program_sequence")
    @SequenceGenerator(name = "loyalty_program_sequence", sequenceName = "loyalty_program_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "GUEST_ID", nullable = false)
    private Integer guestId;

    @Column(name = "POINTS", nullable = false)
    private Integer points = 0;

    @Column(name = "LAST_UPDATED", nullable = false)
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
