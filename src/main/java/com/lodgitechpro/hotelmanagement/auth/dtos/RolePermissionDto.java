package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDto {
    private Integer roleId;
    private List<Integer> permissionIds;
}
