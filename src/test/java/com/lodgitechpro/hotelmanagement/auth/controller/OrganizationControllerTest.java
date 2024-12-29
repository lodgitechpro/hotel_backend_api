package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.entity.Location;
import com.lodgitechpro.hotelmanagement.auth.entity.Organization;
import com.lodgitechpro.hotelmanagement.auth.repository.OrganizationRepository;
import com.lodgitechpro.hotelmanagement.auth.service.LocationService;
import com.lodgitechpro.hotelmanagement.auth.service.OrganizationService;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationControllerTest {

    @Mock
    private OrganizationService organizationService;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private LocationService locationService;

    @InjectMocks
    private OrganizationController organizationController;

    @Test
    public void testFindOrganization_WithValidId() {
        int id = 1; // Provide an existing id for testing
        Organization organization = new Organization(); // Create a mock organization

        when(organizationService.findOrganization(id)).thenReturn(organization); // Mock the service method

        ResponseEntity<Organization> response = organizationController.findOrganization(id); // Call the controller method

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check if the response status is OK
        assertEquals(organization, response.getBody()); // Check if the returned organization matches the expected organization
    }

    @Test
    public void testFindOrganization_WithInvalidId() {
        int id = 999; // Provide a non-existent id for testing

        when(organizationService.findOrganization(id)).thenThrow(new EntityNotFoundException("Organization not found with id " + id)); // Mock the service method to throw EntityNotFoundException

        assertThrows(EntityNotFoundException.class, () -> organizationController.findOrganization(id),
                "Expected EntityNotFoundException should be thrown");
    }

    @Test
    public void testFindAllOrganizations() {
        // Mock organizations
        Organization organization1 = new Organization();
        Organization organization2 = new Organization();
        when(organizationRepository.findAll()).thenReturn(List.of(organization1, organization2));

        ResponseEntity<List<Organization>> response = organizationController.findAllOrganizations(); // Call the controller method

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check if the response status is OK
        assertEquals(List.of(organization1, organization2), response.getBody()); // Check if the returned organizations match the expected organizations
    }

    @Test
    public void testFindOrgLocations() {
        int id = 1; // Provide an existing id for testing
        List<Location> locations = Collections.singletonList(new Location()); // Create mock locations

        when(locationService.findLocationByOrgId(id)).thenReturn(locations); // Mock the service method

        ResponseEntity<List<Location>> response = organizationController.findOrgLocations(id); // Call the controller method

        assertEquals(HttpStatus.OK, response.getStatusCode()); // Check if the response status is OK
        assertEquals(locations, response.getBody()); // Check if the returned locations match the expected locations
    }
}

