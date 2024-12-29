package com.lodgitechpro.hotelmanagement.auth.dtos;

import com.lodgitechpro.hotelmanagement.auth.enums.RoleType;
import lombok.Data;

@Data
public class RoleDto {
    private Integer id;
    private String name;
    private RoleType type;
    private Boolean active;
}
