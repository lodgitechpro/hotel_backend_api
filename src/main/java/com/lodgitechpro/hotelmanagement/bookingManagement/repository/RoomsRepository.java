package com.lodgitechpro.hotelmanagement.bookingManagement.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer>, JpaSpecificationExecutor<Rooms> {

    @Query("SELECT r FROM Rooms r " +
            "WHERE r.locationId = :locationId " +
            "AND r.id NOT IN (" +
            "    SELECT b.roomId " +
            "    FROM Bookings b " +
            "    WHERE (:checkIn BETWEEN b.checkInDate AND b.checkOutDate) " +
            "    OR (:checkOut BETWEEN b.checkInDate AND b.checkOutDate) " +
            "    OR (b.checkInDate BETWEEN :checkIn AND :checkOut)) " +
            "AND r.status = 'AVAILABLE'")
    List<Rooms> findAvailableRooms(@Param("locationId") Integer locationId,
                                   @Param("checkIn") Date checkIn,
                                   @Param("checkOut") Date checkOut);

}
