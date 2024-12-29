package com.lodgitechpro.hotelmanagement.auth.enums;

import lombok.Getter;

@Getter
public enum AuthCode {
    AUTH_SUCCESS(1001, "success"),
    INVALID_CREDENTIALS(1002, "Invalid email or password"),
    ACCOUNT_LOCKED(1003, "Account locked"),
    ACCOUNT_DISABLED(1004, "Account disabled");

    private final int code;
    private final String message;

    AuthCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}