package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PromotionsDto{
    private Integer id;
    private String promotionCode;
    private BigDecimal discountPercentage;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String applicableRoomTypes; // Storing room types as JSON or CSV.
    private Integer maxUsageLimit;
}
