package com.lodgitechpro.hotelmanagement.repository;

import com.lodgitechpro.hotelmanagement.entity.Role;
import com.lodgitechpro.hotelmanagement.entity.RoleForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleFormRepository extends JpaRepository<RoleForm, Long>, JpaSpecificationExecutor<RoleForm> {
    List<RoleForm> findByRole(Role role);
}
