package com.lodgitechpro.hotelmanagement.bookingManagement.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.BookingPaymentDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.BookingPayments;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.PaymentStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.repository.BookingPaymentsRepository;
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
public class BookingPaymentService {

    private final BookingPaymentsRepository bookingPaymentsRepository; // Repository for BookingPayments
    private final EntityMapper entityMapper; // For mapping DTO to entity

    /**
     * Create a new payment.
     *
     * @param bookingPaymentDto the payment details
     * @return the created Payment entity
     */
    public BookingPayments createPayment(BookingPaymentDto bookingPaymentDto) {
        BookingPayments payment = entityMapper.mapDtoToEntity(bookingPaymentDto, BookingPayments.class);
        payment.setPaymentStatus(PaymentStatus.SUCCESS); // Default status for new payments
        payment.setPaymentDate(new Date()); // Set current date as payment date
        BookingPayments savedPayment = bookingPaymentsRepository.save(payment);
        log.info("Payment created with ID: {}", savedPayment.getId());
        return savedPayment;
    }

    /**
     * Get payment details by payment ID.
     *
     * @param paymentId the payment ID
     * @return the Payment entity
     */
    public BookingPayments getPaymentById(Integer paymentId) {
        return bookingPaymentsRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("BookingPayment with ID " + paymentId + " not found."));
    }

    /**
     * Get all payments associated with a specific booking ID.
     *
     * @param bookingId the booking ID
     * @return a list of payments for the booking
     */
    public List<BookingPayments> getPaymentsByBookingId(Integer bookingId) {
        List<BookingPayments> payments = bookingPaymentsRepository.findByBookingId(bookingId);
        if (payments.isEmpty()) {
            throw new EntityNotFoundException("No payments found for Booking ID: " + bookingId);
        }
        log.info("Found {} payments for Booking ID: {}", payments.size(), bookingId);
        return payments;
    }

    /**
     * Refund a payment by ID.
     *
     * @param paymentId the payment ID
     */
    public void refundPayment(Integer paymentId) {
        BookingPayments payment = bookingPaymentsRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment with ID " + paymentId + " not found."));

        // Check if the payment is already refunded
        if (payment.getPaymentStatus() == PaymentStatus.REFUNDED) {
            throw new IllegalStateException("Payment with ID " + paymentId + " is already refunded.");
        }

        // Update the payment status to refunded
        payment.setPaymentStatus(PaymentStatus.REFUNDED);
        bookingPaymentsRepository.save(payment);
        log.info("Payment with ID {} has been refunded.", paymentId);
    }

    /**
     * Update an existing booking payment.
     *
     * @param bookingPaymentId   the ID of the payment to update
     * @param paymentDto  the payment details to update
     * @return the updated BookingPayments entity
     */
    public BookingPayments updateBookingPayment(Integer bookingPaymentId, BookingPaymentDto paymentDto) {
        // Fetch the existing payment from the database
        BookingPayments existingPayment = bookingPaymentsRepository.findById(bookingPaymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment with ID " + bookingPaymentId + " not found."));

        // Update fields from paymentDto if they are not null
        if (paymentDto.getBookingId() != null) {
            existingPayment.setBookingId(paymentDto.getBookingId());
        }
        if (paymentDto.getAmount() != null) {
            existingPayment.setAmount(paymentDto.getAmount());
        }
        if (paymentDto.getPaymentMethod() != null) {
            existingPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        }
        if (paymentDto.getTransactionReference() != null) {
            existingPayment.setTransactionReference(paymentDto.getTransactionReference());
        }
        if (paymentDto.getRemarks() != null) {
            existingPayment.setRemarks(paymentDto.getRemarks());
        }

        // Save the updated payment back to the database
        BookingPayments updatedPayment = bookingPaymentsRepository.save(existingPayment);

        log.info("Updated payment with ID: {}", bookingPaymentId);
        return updatedPayment;
    }

    public List<BookingPayments> searchPaymentBooking(BookingPaymentDto paymentDto) {
        Specification<BookingPayments> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (paymentDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), paymentDto.getId()));
            }

            if (paymentDto.getBookingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingId"), paymentDto.getBookingId()));
            }

            if (paymentDto.getAmount() != null) {
                predicates.add(criteriaBuilder.equal(root.get("amount"), paymentDto.getAmount()));
            }

            if (paymentDto.getPaymentDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentDate"), paymentDto.getPaymentDate()));
            }

            if (paymentDto.getPaymentMethod() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentMethod"), paymentDto.getPaymentMethod()));
            }

            if (paymentDto.getPaymentStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("paymentStatus"), paymentDto.getPaymentStatus()));
            }

            if (StringUtils.isNotBlank(paymentDto.getTransactionReference())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("transactionReference")),
                        "%" + paymentDto.getTransactionReference().toLowerCase() + "%"));
            }

            if (StringUtils.isNotBlank(paymentDto.getRemarks())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("remarks")),
                        "%" + paymentDto.getRemarks().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return bookingPaymentsRepository.findAll(specification, Pageable.unpaged()).getContent();
    }
}
