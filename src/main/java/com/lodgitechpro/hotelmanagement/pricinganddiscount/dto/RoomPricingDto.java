package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class RoomPricingDto {
    private Integer id;
    private String roomType;
    private BigDecimal basePrice;
    private Date startDate;
    private Date endDate;
    private BigDecimal seasonalMultiplier;
    private BigDecimal demandMultiplier;
    private Boolean active;
    private BigDecimal minBasePrice;
    private BigDecimal maxBasePrice;
}
