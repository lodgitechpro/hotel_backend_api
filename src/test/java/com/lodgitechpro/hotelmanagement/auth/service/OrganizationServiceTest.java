package com.lodgitechpro.hotelmanagement.auth.service;

import com.lodgitechpro.hotelmanagement.auth.entity.Organization;
import com.lodgitechpro.hotelmanagement.auth.repository.OrganizationRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @InjectMocks
    private OrganizationService organizationService;

    @Test
    public void testFindOrganization() {
        int id = 1; // Provide an existing id for testing

        Organization organization = new Organization();
        // Set organization properties as needed for testing

        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));

        Organization foundOrganization = organizationService.findOrganization(id);

        assertEquals(organization, foundOrganization, "Found organization should match expected organization");
    }

    @Test
    public void testFindOrganization_WithNonexistentId() {
        int id = 999; // Provide a non-existent id for testing

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> organizationService.findOrganization(id));

        assertEquals("Organization not found with id " + id, exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testFindOrganization_WithNullId() {
        Integer id = null;

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> organizationService.findOrganization(id));

        assertEquals("Please provide id to fetch the organization", exception.getMessage(), "Exception message should match");
    }
}


