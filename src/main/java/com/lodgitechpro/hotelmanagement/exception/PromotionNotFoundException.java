package com.lodgitechpro.hotelmanagement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class PromotionNotFoundException extends RuntimeException {

    private final String code;
    private final String message;

    public PromotionNotFoundException(String code, String message) {
        super(String.format("Error Code: %s, Message: %s", code, message));
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}