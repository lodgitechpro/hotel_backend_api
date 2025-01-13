package com.lodgitechpro.hotelmanagement.pricinganddiscount.repository;

import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Integer>, JpaSpecificationExecutor<LoyaltyProgram> {
    Optional<LoyaltyProgram> findByGuestId(int guestId);
}
