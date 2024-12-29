package com.lodgitechpro.hotelmanagement.bookingManagement.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer>, JpaSpecificationExecutor<Guest> {

    Optional<Guest> findByEmail(String email);

    Optional<Guest> findByLastName(String lastName);
}
