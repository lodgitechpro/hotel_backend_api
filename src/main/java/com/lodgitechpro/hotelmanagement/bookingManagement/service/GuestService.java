package com.lodgitechpro.hotelmanagement.bookingManagement.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.GuestDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Guest;
import com.lodgitechpro.hotelmanagement.bookingManagement.repository.GuestRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestService {

    private final GuestRepository guestRepository;
    private final EntityMapper entityMapper;

    /**
     * Save or update a guest.
     * If the ID is provided in GuestDto, it updates the guest; otherwise, it inserts a new record.
     */
    public Guest saveAndUpdateGuest(GuestDto guestDto) {
        Guest guest = entityMapper.mapDtoToEntity(guestDto, Guest.class);
        guest = guestRepository.save(guest);
        return guest;
    }

    /**
     * Get a guest by ID.
     */
    public Guest getGuestById(Integer guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Guest with ID " + guestId + " not found."));
    }

    /**
     * Get all guests.
     */
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    /**
     * Delete a guest by ID.
     */
    public void deleteGuest(Integer guestId) {
        if (!guestRepository.existsById(guestId)) {
            throw new EntityNotFoundException("Guest with ID " + guestId + " not found.");
        }
        guestRepository.deleteById(guestId);
    }

    /**
     * Search guests dynamically using GuestDto filters.
     */
    public List<Guest> searchGuests(GuestDto guestDto) {
        Specification<Guest> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (guestDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), guestDto.getId()));
            }

            if (StringUtils.isNotBlank(guestDto.getFirstName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + guestDto.getFirstName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(guestDto.getLastName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        "%" + guestDto.getLastName().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(guestDto.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), guestDto.getEmail()));
            }

            if (StringUtils.isNotBlank(guestDto.getPhone())) {
                predicates.add(criteriaBuilder.equal(root.get("phone"), guestDto.getPhone()));
            }

            if (guestDto.getIdDocumentType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("idDocumentType"), guestDto.getIdDocumentType()));
            }

            if (StringUtils.isNotBlank(guestDto.getCity())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")),
                        "%" + guestDto.getCity().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(guestDto.getCountry())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")),
                        "%" + guestDto.getCountry().toLowerCase() + "%"));
            }

            if (guestDto.getZipCode() != null) {
                predicates.add(criteriaBuilder.equal(root.get("zipCode"), guestDto.getZipCode()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return guestRepository.findAll(specification, Pageable.unpaged()).getContent();
    }
}
