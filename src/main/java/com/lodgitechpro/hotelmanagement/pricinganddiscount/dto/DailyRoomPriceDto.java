package com.lodgitechpro.hotelmanagement.pricinganddiscount.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DailyRoomPriceDto {
    List<DailyPricesDto> dailyPricesDtoList;
    private Double totalPrice;
}
