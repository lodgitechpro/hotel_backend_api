package com.lodgitechpro.hotelmanagement.bookingManagement.entity;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.PaymentMethod;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.PaymentStatus;
import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "BOOKING_PAYMENTS")
public class BookingPayments extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_payments_sequence")
    @SequenceGenerator(name = "booking_payments_sequence", sequenceName = "booking_payments_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "BOOKING_ID")
    private Integer bookingId;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "PAYMENT_METHOD")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "TRANSACTION_REFERENCE")
    private String transactionReference;

    @Column(name = "REMARKS")
    private String remarks;
}
