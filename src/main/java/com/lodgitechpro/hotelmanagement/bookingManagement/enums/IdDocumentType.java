package com.lodgitechpro.hotelmanagement.bookingManagement.enums;

import lombok.Getter;

@Getter
public enum IdDocumentType {
    PASSPORT("Passport"),
    DRIVERS_LICENSE("Drivers License"),
    NATIONAL_ID("National ID"),
    VOTER_ID("Voter ID"),
    RESIDENCE_PERMIT("Residence Permit"),
    OTHER("Other");

    private final String message;

    IdDocumentType(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}