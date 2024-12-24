package com.lodgitechpro.hotelmanagement.repository;

import com.lodgitechpro.hotelmanagement.entity.Role;
import com.lodgitechpro.hotelmanagement.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRole(Role role);
}
