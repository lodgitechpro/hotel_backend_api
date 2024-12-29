package com.lodgitechpro.hotelmanagement.auth.service;

import com.lodgitechpro.hotelmanagement.auth.entity.Location;
import com.lodgitechpro.hotelmanagement.auth.repository.LocationRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    public void testFindLocation() {
        int id = 1; // Provide an existing id for testing

        Location location = new Location();
        // Set location properties as needed for testing

        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        Location foundLocation = locationService.findLocation(id);

        assertEquals(location, foundLocation, "Found location should match expected location");
    }

    @Test
    public void testFindLocation_WithNonexistentId() {
        int id = 999; // Provide a non-existent id for testing

        when(locationRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> locationService.findLocation(id));

        assertEquals("Location not found with id " + id, exception.getMessage(), "Exception message should match");
    }

    @Test
    public void testFindLocationByOrgId() {
        int orgId = 1; // Provide an existing orgId for testing

        Location location = new Location();
        // Set location properties as needed for testing

        when(locationRepository.findByOrgId(orgId)).thenReturn(Collections.singletonList(location));

        List<Location> locations = locationService.findLocationByOrgId(orgId);

        assertEquals(1, locations.size(), "Found locations should contain one location");
        assertEquals(location, locations.get(0), "Found location should match expected location");
    }
}

