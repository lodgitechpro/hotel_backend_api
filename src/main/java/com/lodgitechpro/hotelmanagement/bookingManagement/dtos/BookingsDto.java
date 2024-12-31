package com.lodgitechpro.hotelmanagement.bookingManagement.dtos;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.BookingStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingsDto {
    private Integer id;
    private Integer locationId;
    private Integer roomId;
    private Integer guestId;
    private Date checkInDate;
    private Date checkOutDate;
    private Double totalPrice;
    private BookingStatus status;
}
