package com.lodgitechpro.hotelmanagement.auth.repository;

import com.lodgitechpro.hotelmanagement.auth.entity.Role;
import com.lodgitechpro.hotelmanagement.auth.entity.RoleForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleFormRepository extends JpaRepository<RoleForm, Integer>, JpaSpecificationExecutor<RoleForm> {
    List<RoleForm> findByRole(Role role);
}
