package com.lodgitechpro.hotelmanagement.bookingManagement.dtos;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.PaymentMethod;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.PaymentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookingPaymentDto {
    private Integer id;
    private Integer bookingId;
    private Date paymentDate;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionReference;
    private String remarks;
}
