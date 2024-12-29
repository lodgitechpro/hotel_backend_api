package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.entity.Location;
import com.lodgitechpro.hotelmanagement.auth.repository.LocationRepository;
import com.lodgitechpro.hotelmanagement.auth.service.LocationService;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationController locationController;

    @Test
    public void testFindLocation_WithValidId() {
        Integer id = 1; // Provide an existing id for testing
        Location location = new Location();
        // Set location properties as needed for testing

        when(locationService.findLocation(id)).thenReturn(location);

        ResponseEntity<Location> response = locationController.findLocation(id);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertEquals(location, response.getBody(), "Returned location should match expected location");
    }

    @Test
    public void testFindLocation_WithInvalidId() {
        Integer id = 999; // Provide a non-existent id for testing

        when(locationService.findLocation(id)).thenThrow(new EntityNotFoundException("Location not found with id " + id));

        assertThrows(EntityNotFoundException.class, () -> locationController.findLocation(id),
                "Expected EntityNotFoundException should be thrown");
    }

    @Test
    public void testFindAllLocations() {
        List<Location> locations = new ArrayList<>();
        // Add some locations to the list as needed for testing

        when(locationRepository.findAll()).thenReturn(locations);

        ResponseEntity<List<Location>> response = locationController.findAllLocations();

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status should be OK");
        assertEquals(locations, response.getBody(), "Returned locations should match expected locations");
    }
}

