package com.lodgitechpro.hotelmanagement.bookingManagement.controller;


import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.BookingsDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Bookings;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.BookingStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new booking.
     */
    @PostMapping
    public ResponseEntity<Bookings> createBooking(@RequestBody @Valid BookingsDto bookingDto) {
        Bookings booking = bookingService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    /**
     * Update an existing booking by ID.
     */
    @PutMapping("/{bookingId}")
    public ResponseEntity<Bookings> updateBooking(
            @PathVariable Integer bookingId,
            @RequestBody @Valid BookingsDto bookingDto) {
        Bookings updatedBooking = bookingService.updateBooking(bookingId, bookingDto);
        return ResponseEntity.ok(updatedBooking);
    }

    /**
     * Get a booking by ID.
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<Bookings> getBookingById(@PathVariable Integer bookingId) {
        Bookings booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(booking);
    }

    /**
     * Get all bookings.
     */
    @GetMapping
    public ResponseEntity<List<Bookings>> getAllBookings() {
        List<Bookings> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    /**
     * Delete a booking by ID.
     */
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search bookings dynamically using filters.
     */
    @PostMapping("/search")
    public ResponseEntity<List<Bookings>> searchBookings(@RequestBody BookingsDto bookingDto) {
        List<Bookings> bookings = bookingService.searchBookings(bookingDto);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Update the status of a booking.
     */
    @PatchMapping("/{bookingId}/status")
    public ResponseEntity<Void> updateBookingStatus(
            @PathVariable Integer bookingId,
            @RequestParam BookingStatus status) {
        bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all bookings for a specific guest by guest ID.
     */
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<Bookings>> getBookingsByGuestId(@PathVariable Integer guestId) {
        List<Bookings> bookings = bookingService.getBookingsByGuestId(guestId);
        return ResponseEntity.ok(bookings);
    }

    /**
     * Cancel a booking by ID.
     */
    @PatchMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Integer bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}
