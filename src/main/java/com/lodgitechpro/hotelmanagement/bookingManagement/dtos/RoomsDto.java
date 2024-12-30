package com.lodgitechpro.hotelmanagement.bookingManagement.dtos;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.IdDocumentType;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class RoomsDto {
    private Integer id;
    private Integer locationId;
    private String roomNumber;
    private RoomType roomType;
    private int beds;
    private int maxOccupancy;
    private Double pricePerNight;
    private RoomStatus status;
}
