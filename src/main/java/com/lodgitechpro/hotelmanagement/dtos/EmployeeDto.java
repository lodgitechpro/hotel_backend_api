package com.lodgitechpro.hotelmanagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    private Long employeeId;
    private Long roleId;
    private List<Long> roleIds;
    private String username;

}
