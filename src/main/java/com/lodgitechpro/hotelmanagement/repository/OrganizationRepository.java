package com.lodgitechpro.hotelmanagement.repository;

import com.lodgitechpro.hotelmanagement.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
