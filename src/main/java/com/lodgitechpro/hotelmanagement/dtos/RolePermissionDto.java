package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDto {
    private Long roleId;
    private List<Long> permissionIds;
}
