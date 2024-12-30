package com.lodgitechpro.hotelmanagement.bookingManagement.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.RoomsDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Rooms;
import com.lodgitechpro.hotelmanagement.bookingManagement.repository.RoomsRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomsService {

    private final EntityMapper entityMapper;
    private final RoomsRepository roomsRepository;

    public Rooms saveAndUpdateRooms(RoomsDto roomsDto) {
        Rooms rooms = entityMapper.mapDtoToEntity(roomsDto, Rooms.class);
        rooms = roomsRepository.save(rooms);
        return rooms;
    }

    public Rooms getRoomById(Integer roomId) {
        return roomsRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with ID " + roomId + " not found."));
    }

    public List<Rooms> getAllRooms() {
        return roomsRepository.findAll();
    }

    public void deleteRoom(Integer roomId) {
        if (!roomsRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room with ID " + roomId + " not found.");
        }
        roomsRepository.deleteById(roomId);
    }

    public List<Rooms> searchRooms(RoomsDto roomDto) {
        Specification<Rooms> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (roomDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), roomDto.getId()));
            }

            if (roomDto.getLocationId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("locationId"), roomDto.getLocationId()));
            }

            if (StringUtils.isNotBlank(roomDto.getRoomNumber())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roomNumber")),
                        "%" + roomDto.getRoomNumber().toLowerCase() + "%"));
            }

            if (roomDto.getRoomType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomType"), roomDto.getRoomType()));
            }

            if (roomDto.getBeds() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("beds"), roomDto.getBeds()));
            }

            if (roomDto.getMaxOccupancy() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("maxOccupancy"), roomDto.getMaxOccupancy()));
            }

            if (roomDto.getPricePerNight() != null) {
                predicates.add(criteriaBuilder.equal(root.get("pricePerNight"), roomDto.getPricePerNight()));
            }

            if (roomDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), roomDto.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return roomsRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    public List<Rooms> getAvailableRooms(Integer locationId, Date checkIn, Date checkOut) {
        return roomsRepository.findAvailableRooms(locationId, checkIn, checkOut);
    }
}
