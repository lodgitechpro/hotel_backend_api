package com.lodgitechpro.hotelmanagement.auth.controller;

import com.lodgitechpro.hotelmanagement.auth.entity.Location;
import com.lodgitechpro.hotelmanagement.auth.entity.Organization;
import com.lodgitechpro.hotelmanagement.auth.repository.OrganizationRepository;
import com.lodgitechpro.hotelmanagement.auth.service.LocationService;
import com.lodgitechpro.hotelmanagement.auth.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private LocationService locationService;

    @GetMapping("/{id}")
    public ResponseEntity<Organization> findOrganization(@PathVariable int id) {
        Organization organization = organizationService.findOrganization(id);
        return ResponseEntity.ok(organization);
    }

    @GetMapping
    public ResponseEntity<List<Organization>> findAllOrganizations() {
        var organizations = organizationRepository.findAll();
        return ResponseEntity.ok(organizations);
    }

    @GetMapping("/{id}/locations")
    public ResponseEntity<List<Location>> findOrgLocations(@PathVariable Integer id) {
        List<Location> locations = locationService.findLocationByOrgId(id);
        return ResponseEntity.ok(locations);
    }

}
