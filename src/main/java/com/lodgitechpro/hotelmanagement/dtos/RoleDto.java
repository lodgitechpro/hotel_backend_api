package com.lodgitechpro.hotelmanagement.dtos;

import com.lodgitechpro.hotelmanagement.enums.RoleType;
import lombok.Data;

@Data
public class RoleDto {
    private Long id;
    private String name;
    private RoleType type;
    private Boolean active;
}
