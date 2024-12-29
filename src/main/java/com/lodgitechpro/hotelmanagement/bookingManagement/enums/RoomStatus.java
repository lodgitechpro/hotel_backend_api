package com.lodgitechpro.hotelmanagement.bookingManagement.enums;

import lombok.Getter;

@Getter
public enum RoomStatus {
    AVAILABLE("Available"),
    BOOKED("Booked"),
    OCCUPIED("Occupied"),
    UNDER_MAINTENANCE("Under Maintenance"),
    CLEANING_IN_PROGRESS("Cleaning In Progress"),
    OUT_OF_SERVICE("Out of Service");

    private final String displayName;

    RoomStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
