package com.lodgitechpro.hotelmanagement.bookingManagement.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PAYPAL("PayPal"),
    CASH("Cash"),
    BANK_TRANSFER("Bank Transfer"),
    MOBILE_PAYMENT("Mobile Payment"),
    OTHER("Other");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
