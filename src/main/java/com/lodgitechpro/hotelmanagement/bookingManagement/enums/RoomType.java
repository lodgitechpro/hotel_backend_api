package com.lodgitechpro.hotelmanagement.bookingManagement.enums;

import lombok.Getter;

@Getter
public enum RoomType {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    SUITE("Suite"),
    DELUXE("Deluxe Room"),
    FAMILY("Family Room"),
    STUDIO("Studio Room"),
    TWIN("Twin Room"),
    PRESIDENTIAL_SUITE("Presidential Suite"),
    OTHER("Other");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
