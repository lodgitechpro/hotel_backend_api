package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.entity.Location;
import com.lodgitechpro.hotelmanagement.auth.repository.LocationRepository;
import com.lodgitechpro.hotelmanagement.auth.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Location> findLocation(@PathVariable Integer id) {
        Location location = locationService.findLocation(id);
        return ResponseEntity.ok(location);
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAllLocations() {
        var locations = locationRepository.findAll();
        return ResponseEntity.ok(locations);
    }

}
