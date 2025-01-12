package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class PromotionDto {
    private Integer id;
    private String promotionCode;
    private BigDecimal discountPercentage;
    private LocalDate validFrom;
    private LocalDate validTo;
    private List<String> applicableRoomTypes; // Storing room types as JSON or CSV.
    private Integer maxUsageLimit;
    private Boolean active;
}
