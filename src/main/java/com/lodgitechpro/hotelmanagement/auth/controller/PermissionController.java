package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.dtos.PermissionDto;
import com.lodgitechpro.hotelmanagement.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getPermissions(@RequestBody PermissionDto permissionDto) {
        var permissions =  roleService.searchPermissions(permissionDto);
        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    public ResponseEntity<?> savePermission(@RequestBody PermissionDto permissionDto) {
        var permission =  roleService.saveAndUpdatePermission(permissionDto);
        return ResponseEntity.ok(permission);
    }
}
