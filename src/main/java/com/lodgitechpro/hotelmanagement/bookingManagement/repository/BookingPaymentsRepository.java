package com.lodgitechpro.hotelmanagement.bookingManagement.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.entity.BookingPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingPaymentsRepository extends JpaRepository<BookingPayments, Integer>, JpaSpecificationExecutor<BookingPayments> {

    List<BookingPayments> findByBookingId(int bookingId);
}
