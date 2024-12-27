package com.lodgitechpro.hotelmanagement.controller;

import com.lodgitechpro.hotelmanagement.dtos.RoleDto;
import com.lodgitechpro.hotelmanagement.dtos.RoleFormDto;
import com.lodgitechpro.hotelmanagement.dtos.RolePermissionDto;
import com.lodgitechpro.hotelmanagement.entity.Role;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.repository.RoleRepository;
import com.lodgitechpro.hotelmanagement.service.MenuFormService;
import com.lodgitechpro.hotelmanagement.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuFormService menuFormService;

    @PostMapping
    public ResponseEntity<?> saveRole(
            @RequestBody RoleDto roleDto
    ) {
        Role role = roleService.saveAndUpdateRole(roleDto);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(
            @PathVariable long id
    ) {
        var role =  roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public List<Role> getRoles(@RequestBody RoleDto roleDto) {
        List<Role> roles =  roleService.searchRoles(roleDto);
        return roles;
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<?> getRolePermissions(@PathVariable long id) {
        var roles =  roleService.getRolePermissions(id);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/permissions")
    public ResponseEntity<?> saveRolePermissions(
            @RequestBody RolePermissionDto rolePermissionDto
    ) {
        if(rolePermissionDto.getRoleId() == null || rolePermissionDto.getPermissionIds() == null) {
            throw new IllegalArgumentException("RoleId and Permission id list is required to save permission roles");
        }
        roleService.saveRolePermissions(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/permissions/copy")
    public ResponseEntity<?> copyRolePermissions(
            @RequestParam("fromRoleId") long fromRoleId,
            @RequestParam("toRoleId") long toRoleId) {
        roleService.copyRolePermissions(fromRoleId, toRoleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/forms")
    public ResponseEntity<?> getRoleForms(@PathVariable long id) {
        var forms =  menuFormService.getRoleForms(id);
        return ResponseEntity.ok(forms);
    }

    @PostMapping("/forms")
    public ResponseEntity<?> saveRoleForms(
            @RequestBody RoleFormDto roleFormDto
    ) {
        if(roleFormDto.getRoleId() == null || roleFormDto.getFormIds() == null) {
            throw new IllegalArgumentException("RoleId and Form id list is required to save role forms");
        }
        menuFormService.saveRoleForm(roleFormDto.getRoleId(), roleFormDto.getFormIds());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forms/copy")
    public ResponseEntity<?> copyRoleForms(
            @RequestParam("fromRoleId") long fromRoleId,
            @RequestParam("toRoleId") long toRoleId) {
        menuFormService.copyRoleForms(fromRoleId, toRoleId);
        return ResponseEntity.ok().build();
    }
}