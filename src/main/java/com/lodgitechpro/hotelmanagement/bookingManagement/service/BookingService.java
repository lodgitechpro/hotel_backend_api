package com.lodgitechpro.hotelmanagement.bookingManagement.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.BookingsDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Bookings;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.BookingStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.repository.BookingsRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingsRepository bookingRepository;
    private final EntityMapper entityMapper;

    /**
     * Save or update a booking.
     * If the ID is provided in BookingDto, it updates the booking; otherwise, it inserts a new record.
     */
    public Bookings saveOrUpdateBooking(BookingsDto bookingDto) {
        Bookings booking = entityMapper.mapDtoToEntity(bookingDto, Bookings.class);
        booking = bookingRepository.save(booking);
        return booking;
    }


    /**
     * Create a new booking.
     */
    public Bookings createBooking(BookingsDto bookingDto) {
        Bookings booking = entityMapper.mapDtoToEntity(bookingDto, Bookings.class);
        booking.setStatus(BookingStatus.CONFIRMED); // Default status
        booking = bookingRepository.save(booking);
        log.info("Booking created with ID: {}", booking.getId());
        return booking;
    }


    /**
     * Update an existing booking by ID.
     */
    public Bookings updateBooking(Integer bookingId, BookingsDto bookingDto) {
        Bookings existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookingId + " not found."));

        entityMapper.mapDtoToEntity(bookingDto, existingBooking); // Map updates to existing entity
        existingBooking = bookingRepository.save(existingBooking);
        log.info("Booking updated with ID: {}", bookingId);
        return existingBooking;
    }


    /**
     * Get a booking by ID.
     */
    public Bookings getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookingId + " not found."));
    }

    /**
     * Get all bookings.
     */
    public List<Bookings> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Delete a booking by ID.
     */
    public void deleteBooking(Integer bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found.");
        }
        bookingRepository.deleteById(bookingId);
    }

    /**
     * Search bookings dynamically using BookingDto filters.
     */
    public List<Bookings> searchBookings(BookingsDto bookingDto) {
        Specification<Bookings> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (bookingDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), bookingDto.getId()));
            }

            if (bookingDto.getLocationId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("locationId"), bookingDto.getLocationId()));
            }

            if (bookingDto.getRoomId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomId"), bookingDto.getRoomId()));
            }

            if (bookingDto.getGuestId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("guestId"), bookingDto.getGuestId()));
            }

            if (bookingDto.getCheckInDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("checkInDate"), bookingDto.getCheckInDate()));
            }

            if (bookingDto.getCheckOutDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("checkOutDate"), bookingDto.getCheckOutDate()));
            }

            if (bookingDto.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), bookingDto.getStatus()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return bookingRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    /**
     * Update the status of a booking.
     */
    public void updateBookingStatus(Integer bookingId, BookingStatus status) {
        Bookings booking = getBookingById(bookingId); // Reuse the method to fetch the booking
        booking.setStatus(status);
        bookingRepository.save(booking);
    }


    /**
     * Get all bookings for a specific guest by guest ID.
     */
    public List<Bookings> getBookingsByGuestId(Integer guestId) {
        List<Bookings> bookings = bookingRepository.findByGuestId(guestId);
        if (bookings.isEmpty()) {
            throw new EntityNotFoundException("No bookings found for Guest ID: " + guestId);
        }
        log.info("Found {} bookings for Guest ID: {}", bookings.size(), guestId);
        return bookings;
    }


    /**
     * Cancel a booking by ID.
     */
    public void cancelBooking(Integer bookingId) {
        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking with ID " + bookingId + " not found."));

        booking.setStatus(BookingStatus.CANCELLED); // Set status to canceled
        bookingRepository.save(booking);
        log.info("Booking canceled with ID: {}", bookingId);
    }
}
