package com.lodgitechpro.hotelmanagement.bookingManagement.controller;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.BookingPaymentDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.BookingPayments;
import com.lodgitechpro.hotelmanagement.bookingManagement.service.BookingPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking-payments")
public class BookingPaymentController {

    private final BookingPaymentService bookingPaymentService;

    // 1. Create Payment
    @PostMapping
    public ResponseEntity<BookingPayments> createPayment(@RequestBody BookingPaymentDto paymentDto) {
        BookingPayments payment = bookingPaymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    // 2. Get Payment by ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<BookingPayments> getPaymentById(@PathVariable Integer paymentId) {
        BookingPayments payment = bookingPaymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    // 3. Get Payments by Booking ID
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<BookingPayments>> getPaymentsByBookingId(@PathVariable Integer bookingId) {
        List<BookingPayments> payments = bookingPaymentService.getPaymentsByBookingId(bookingId);
        return ResponseEntity.ok(payments);
    }

    // 4. Refund Payment
    @PostMapping("/refund/{paymentId}")
    public ResponseEntity<Void> refundPayment(@PathVariable Integer paymentId) {
        bookingPaymentService.refundPayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    // 5. Update Payment
    @PutMapping("/{paymentId}")
    public ResponseEntity<BookingPayments> updatePayment(
            @PathVariable Integer paymentId,
            @RequestBody BookingPaymentDto bookingPaymentDto) {
        BookingPayments updatedPayment = bookingPaymentService.updateBookingPayment(paymentId, bookingPaymentDto);
        return ResponseEntity.ok(updatedPayment);
    }

    // 6. Search Payments
    @PostMapping("/search")
    public ResponseEntity<List<BookingPayments>> searchPaymentBooking(@RequestBody BookingPaymentDto paymentDto) {
        List<BookingPayments> payments = bookingPaymentService.searchPaymentBooking(paymentDto);
        return ResponseEntity.ok(payments);
    }
}
