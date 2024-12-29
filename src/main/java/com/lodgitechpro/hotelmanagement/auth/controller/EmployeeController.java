package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.dtos.ChangePasswordRequest;
import com.lodgitechpro.hotelmanagement.auth.dtos.EmployeeDto;
import com.lodgitechpro.hotelmanagement.auth.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PatchMapping("/password/change")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role")
    public ResponseEntity<?> saveEmployeeRole(
            @RequestBody EmployeeDto employeeDto
    ) {
        if(employeeDto.getEmployeeId() == null || employeeDto.getRoleId() == null) {
            throw new IllegalArgumentException("EmployeeId and RoleId is required to save employee roles");
        }

        service.saveEmployeeRole(employeeDto.getEmployeeId(), employeeDto.getRoleId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles")
    public ResponseEntity<?> saveEmployeeRoles(
            @RequestBody EmployeeDto employeeDto
    ) {
        if(employeeDto.getEmployeeId() == null || employeeDto.getRoleIds() == null) {
            throw new IllegalArgumentException("EmployeeId and RoleId list is required to save employee roles");
        }
        service.saveEmployeeRoles(employeeDto.getEmployeeId(), employeeDto.getRoleIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles/copy")
    public ResponseEntity<?> copyEmployeeRoles(
            @RequestParam("fromEmpId") int fromEmpId,
            @RequestParam("toEmpId") int toEmpId) {
        service.copyEmployeeRoles(fromEmpId, toEmpId);
        return ResponseEntity.ok().build();
    }
}
