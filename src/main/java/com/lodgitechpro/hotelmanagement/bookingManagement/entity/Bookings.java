package com.lodgitechpro.hotelmanagement.bookingManagement.entity;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.BookingStatus;
import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "BOOKINGS")
public class Bookings extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_sequence")
    @SequenceGenerator(name = "bookings_sequence", sequenceName = "bookings_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "LOCATION_ID")
    private Integer locationId;

    @Column(name = "ROOM_ID")
    private Integer roomId;

    @Column(name = "GUEST_ID")
    private Integer guestId;

    @Column(name = "CHECK_IN_DATE")
    private Date checkInDate;

    @Column(name = "CHECK_OUT_DATE")
    private Date checkOutDate;

    @Column(name = "TOTAL_PRICE")
    private Double totalPrice;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
