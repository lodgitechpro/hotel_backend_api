package com.lodgitechpro.hotelmanagement.pricinganddiscount.repository;


import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.RoomPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomPricingRepository extends JpaRepository<RoomPricing, Integer>, JpaSpecificationExecutor<RoomPricing> {

    Optional<RoomPricing> findByRoomType(RoomType roomType);

}
