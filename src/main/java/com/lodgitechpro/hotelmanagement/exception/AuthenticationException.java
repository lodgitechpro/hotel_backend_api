package com.lodgitechpro.hotelmanagement.exception;


import com.lodgitechpro.hotelmanagement.enums.AuthCode;

public class AuthenticationException extends RuntimeException {

    private final AuthCode authCode;

    public AuthenticationException(AuthCode authCode) {
        super(authCode.getMessage());
        this.authCode = authCode;
    }

    public AuthCode getAuthCode() {
        return authCode;
    }
}