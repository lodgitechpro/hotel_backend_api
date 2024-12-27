package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

@Data
public class PermissionDto {
    private Long id;
    private String name;
    private Boolean active;
}
