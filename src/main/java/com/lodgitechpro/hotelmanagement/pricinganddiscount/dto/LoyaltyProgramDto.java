package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoyaltyProgramDto {
    private Integer id;
    private Integer guestId;
    private Integer points;
    private LocalDateTime lastUpdated = LocalDateTime.now();
}
