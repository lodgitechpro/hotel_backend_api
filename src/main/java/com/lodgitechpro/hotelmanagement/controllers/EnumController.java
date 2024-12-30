package com.lodgitechpro.hotelmanagement.controllers;

import com.lodgitechpro.hotelmanagement.auth.dtos.EnumDto;
import com.lodgitechpro.hotelmanagement.auth.enums.RoleType;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping("/room-types")
    public List<EnumDto> getRoomTypes() {
        return Arrays.stream(RoomType.values())
                .map(rt -> new EnumDto(rt.name(), rt.getDisplayName()))
                .toList();
    }

    @GetMapping("/role-types")
    public List<EnumDto> getRoleTypes() {
        return Arrays.stream(RoleType.values())
                .map(rt -> new EnumDto(rt.name(), rt.name()))
                .toList();
    }

    @GetMapping("/booking-status")
    public List<EnumDto> getBookingStatus() {
        return Arrays.stream(BookingStatus.values())
                .map(rt -> new EnumDto(rt.name(), rt.getDisplayName()))
                .toList();
    }

    @GetMapping("/id-document-type")
    public List<EnumDto> getIdDocumentType() {
        return Arrays.stream(IdDocumentType.values())
                .map(rt -> new EnumDto(rt.name(), rt.getMessage()))
                .toList();
    }

    @GetMapping("/payment-method")
    public List<EnumDto> getPaymentMethod() {
        return Arrays.stream(PaymentMethod.values())
                .map(rt -> new EnumDto(rt.name(), rt.getDisplayName()))
                .toList();
    }

    @GetMapping("/payment-status")
    public List<EnumDto> getPaymentStatus() {
        return Arrays.stream(PaymentStatus.values())
                .map(rt -> new EnumDto(rt.name(), rt.getDisplayName()))
                .toList();
    }

}
