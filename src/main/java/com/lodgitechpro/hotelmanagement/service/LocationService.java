package com.lodgitechpro.hotelmanagement.service;

import com.lodgitechpro.hotelmanagement.entity.Location;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location findLocation(Long id) {
        Optional.ofNullable(id)
                .orElseThrow(() -> new RuntimeException("Please provide id to fetch the location"));

        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id " + id));
    }

    public List<Location> findLocationByOrgId(long orgId) {
        return locationRepository.findByOrgId(orgId);
    }
}
