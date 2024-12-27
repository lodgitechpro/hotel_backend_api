package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RoleFormDto {
    private Long roleId;
    private List<Long> formIds;
}
