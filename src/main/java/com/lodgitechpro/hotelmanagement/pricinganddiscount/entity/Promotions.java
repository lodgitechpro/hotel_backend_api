package com.lodgitechpro.hotelmanagement.pricinganddiscount.entity;

import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "PROMOTIONS")
public class Promotions extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotions_sequence")
    @SequenceGenerator(name = "promotions_sequence", sequenceName = "promotions_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "PROMOTION_CODE", nullable = false, unique = true, length = 100)
    private String promotionCode;

    @Column(name = "DISCOUNT_PERCENTAGE", nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "VALID_FROM", nullable = false)
    private LocalDate validFrom;

    @Column(name = "VALID_TO", nullable = false)
    private LocalDate validTo;

    @Column(name = "APPLICABLE_ROOM_TYPES", columnDefinition = "TEXT")
    private String applicableRoomTypes; // Storing room types as JSON or CSV.

    @Column(name = "MAX_USAGE_LIMIT")
    private Integer maxUsageLimit;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;
}
