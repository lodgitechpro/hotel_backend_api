package com.lodgitechpro.hotelmanagement.auth.dtos;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDto {

    private Integer employeeId;
    private Integer roleId;
    private List<Integer> roleIds;
    private String username;

}
