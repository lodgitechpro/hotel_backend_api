package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

@Data
public class PermissionDto {
    private Integer id;
    private String name;
    private Boolean active;
}
