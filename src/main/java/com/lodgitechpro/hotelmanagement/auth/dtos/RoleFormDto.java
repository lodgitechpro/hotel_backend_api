package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoleFormDto {
    private Integer roleId;
    private List<Integer> formIds;
}
