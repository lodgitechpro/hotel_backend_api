package com.lodgitechpro.hotelmanagement.auth.repository;

import com.lodgitechpro.hotelmanagement.auth.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
