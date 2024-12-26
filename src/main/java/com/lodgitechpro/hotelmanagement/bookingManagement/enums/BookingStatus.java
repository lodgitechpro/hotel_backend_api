package com.lodgitechpro.hotelmanagement.bookingManagement.enums;

import lombok.Getter;

@Getter
public enum BookingStatus {
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled"),
    CHECKED_IN("Checked In"),
    CHECKED_OUT("Checked Out"),
    PENDING("Pending"),
    NO_SHOW("No Show");

    private final String displayName;

    BookingStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
