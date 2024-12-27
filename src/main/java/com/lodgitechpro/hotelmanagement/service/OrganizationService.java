package com.lodgitechpro.hotelmanagement.service;

import com.lodgitechpro.hotelmanagement.entity.Organization;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization findOrganization(Long id) {
        Optional.ofNullable(id)
                .orElseThrow(() -> new RuntimeException("Please provide id to fetch the organization"));

        return organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with id " + id));
    }
}
