package com.lodgitechpro.hotelmanagement.pricinganddiscount.entity;

import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ROOM_PRICING")
public class RoomPricing extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_pricing_sequence")
    @SequenceGenerator(name = "room_pricing_sequence", sequenceName = "room_pricing_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "ROOM_TYPE", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "BASE_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @Column(name = "SEASONAL_MULTIPLIER", precision = 5, scale = 2, columnDefinition = "NUMERIC(5,2) DEFAULT 1.0")
    private BigDecimal seasonalMultiplier = BigDecimal.valueOf(1.0);

    @Column(name = "DEMAND_MULTIPLIER", precision = 5, scale = 2, columnDefinition = "NUMERIC(5,2) DEFAULT 1.0")
    private BigDecimal demandMultiplier = BigDecimal.valueOf(1.0);
}
