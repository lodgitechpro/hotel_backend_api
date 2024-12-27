package com.lodgitechpro.hotelmanagement.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXIST(2001, "User already registered");

    private final int code;
    private final String message;
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
