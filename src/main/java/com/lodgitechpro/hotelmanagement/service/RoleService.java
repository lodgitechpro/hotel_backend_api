package com.lodgitechpro.hotelmanagement.service;

import com.lodgitechpro.hotelmanagement.dtos.PermissionDto;
import com.lodgitechpro.hotelmanagement.dtos.RoleDto;
import com.lodgitechpro.hotelmanagement.entity.Permission;
import com.lodgitechpro.hotelmanagement.entity.Role;
import com.lodgitechpro.hotelmanagement.entity.RolePermission;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import com.lodgitechpro.hotelmanagement.repository.PermissionRepository;
import com.lodgitechpro.hotelmanagement.repository.RolePermissionRepository;
import com.lodgitechpro.hotelmanagement.repository.RoleRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService {

    private final EntityMapper entityMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;


    public Role saveAndUpdateRole(RoleDto roleDto) {
        // if id is given in roleDto then it will update else it will try to insert.
        Role role = entityMapper.mapDtoToEntity(roleDto, Role.class);
        role = roleRepository.save(role);
        return role;
    }

    public List<Role> searchRoles(RoleDto roleDto) {
        Specification<Role> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (roleDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), roleDto.getId()));
            }

            if (StringUtils.isNotBlank(roleDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + roleDto.getName().toLowerCase() + "%"));
            }

            if (roleDto.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), roleDto.getType()));
            }

            if (roleDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), roleDto.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return roleRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public Permission saveAndUpdatePermission(PermissionDto permissionDto) {
        // if id is given in permissionDto then it will update else it will try to insert.
        Permission permission = entityMapper.mapDtoToEntity(permissionDto, Permission.class);
        permission = permissionRepository.save(permission);
        return permission;
    }

    public List<Permission> searchPermissions(PermissionDto permissionDto) {
        Specification<Permission> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (permissionDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), permissionDto.getId()));
            }

            if (StringUtils.isNotBlank(permissionDto.getName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + permissionDto.getName().toLowerCase() + "%"));
            }

            if (permissionDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), permissionDto.getActive()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return permissionRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public void saveRolePermissions(long roleId, List<Long> permissionIds) {
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        if(permissions.isEmpty()) {
            log.error("Permissions not found with given permissionIds. No role permissions will be saved! " + permissionIds);
            return;
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ roleId));
        List<RolePermission> rolePermissions = new ArrayList<>();
        for(Permission permission : permissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(role);
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);
        log.info("New {} permissions are added with roleId {}", permissions.size(), role.getId());
    }

    public List<PermissionDto> getRolePermissions(long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id "+ roleId));

        List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(role);
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .map(permission -> entityMapper.mapDtoToEntity(permission, PermissionDto.class))
                .collect(Collectors.toList());
    }

    public void copyRolePermissions(long fromRoleId, long toRoleId) {

        Role fromRole = roleRepository.findById(fromRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ fromRoleId));

        Role toRole = roleRepository.findById(toRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: "+ toRoleId));


        List<Permission> fromPermissions = rolePermissionRepository.findByRole(fromRole).stream()
                .map(RolePermission::getPermission)
                .toList();

        List<RolePermission> rolePermissions = new ArrayList<>();
        for(Permission permission : fromPermissions) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermission(permission);
            rolePermission.setRole(toRole);
            rolePermissions.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissions);
        log.info("Permissions Copy Successful! {} permissions are copied fromRoleId {} toRoleId {}", fromPermissions.size(), fromRoleId, toRoleId);
    }

}
