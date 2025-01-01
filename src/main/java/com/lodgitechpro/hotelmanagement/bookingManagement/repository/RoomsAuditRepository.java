package com.lodgitechpro.hotelmanagement.bookingManagement.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.entity.RoomsAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsAuditRepository extends JpaRepository<RoomsAudit, Integer> {
    List<RoomsAudit> findByRoomId(int roomId);
}
