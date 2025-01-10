package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DailyPricesDto {
    private Date date;
    private Double price;
}
