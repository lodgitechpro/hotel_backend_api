package com.lodgitechpro.hotelmanagement.auth.repository;

import com.lodgitechpro.hotelmanagement.auth.entity.Role;
import com.lodgitechpro.hotelmanagement.auth.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    List<RolePermission> findByRole(Role role);
}
