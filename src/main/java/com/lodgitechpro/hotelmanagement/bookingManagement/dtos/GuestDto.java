package com.lodgitechpro.hotelmanagement.bookingManagement.dtos;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.IdDocumentType;
import lombok.Data;

@Data
public class GuestDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private IdDocumentType idDocumentType;
    private String idDocumentNumber;
    private String streetAddress;
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
}
