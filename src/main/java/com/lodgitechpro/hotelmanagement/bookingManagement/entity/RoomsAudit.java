package com.lodgitechpro.hotelmanagement.bookingManagement.entity;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ROOMS_AUDIT")
public class RoomsAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rooms_audit_sequence")
    @SequenceGenerator(name = "rooms_audit_sequence", sequenceName = "rooms_audit_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "LOCATION_ID")
    private Integer locationId;

    @Column(name = "ROOM_ID")
    private Integer roomId;

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

    @Column(name = "PREVIOUS_STATUS")
    @Enumerated(EnumType.STRING)
    private RoomStatus previous_status;

    @Column(name = "CHANGED_BY", nullable = false, updatable = false)
    @CreatedBy
    private String changedBy;

    @Column(name = "CHANGED_AT", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime changedAt;

    @Column(name = "change_session_id")
    private String changeSessionId;
}
