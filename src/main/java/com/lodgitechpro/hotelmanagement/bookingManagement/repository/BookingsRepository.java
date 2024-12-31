package com.lodgitechpro.hotelmanagement.bookingManagement.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer>, JpaSpecificationExecutor<Bookings> {

     List<Bookings> findByGuestId(int guestId);

}
