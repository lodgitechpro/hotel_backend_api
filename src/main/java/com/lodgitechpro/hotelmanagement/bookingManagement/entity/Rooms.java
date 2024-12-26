package com.lodgitechpro.hotelmanagement.bookingManagement.entity;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import com.lodgitechpro.hotelmanagement.auth.entity.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ROOMS")
public class Rooms extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rooms_sequence")
    @SequenceGenerator(name = "rooms_sequence", sequenceName = "rooms_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "LOCATION_ID")
    private Integer locationId;

    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Column(name = "ROOM_TYPE")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "BEDS")
    private int beds;

    @Column(name = "MAX_OCCUPANCY")
    private int maxOccupancy;

    @Column(name = "PRICE_PER_NIGHT")
    private String pricePerNight;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
}
