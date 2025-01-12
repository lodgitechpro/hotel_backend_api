package com.lodgitechpro.hotelmanagement.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class PromotionErrorResponse {
    private HttpStatus status;
    private String message;
    private String code;
}
