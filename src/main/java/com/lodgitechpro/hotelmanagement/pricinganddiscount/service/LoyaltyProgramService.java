package com.lodgitechpro.hotelmanagement.pricinganddiscount.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.repository.GuestRepository;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.LoyaltyProgramDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.LoyaltyProgram;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.repository.LoyaltyProgramRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoyaltyProgramService {

    private final LoyaltyProgramRepository loyaltyProgramRepository;
    private final GuestRepository guestRepository;

    /**
     * Add loyalty points for a guest based on the amount spent.
     */
    public void addLoyaltyPoints(Integer guestId, BigDecimal amountSpent) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findByGuestId(guestId)
                .orElseGet(() -> createNewLoyaltyProgram(guestId));

        int pointsToAdd = calculatePoints(amountSpent);
        loyaltyProgram.setPoints(loyaltyProgram.getPoints() + pointsToAdd);

        loyaltyProgramRepository.save(loyaltyProgram);
    }

    /**
     * Redeem loyalty points for a guest.
     */
    public Boolean redeemPoints(Integer guestId, Integer pointsToRedeem) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findByGuestId(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Loyalty details for guest ID " + guestId + " not found."));

        if (loyaltyProgram.getPoints() < pointsToRedeem) {
            return false;
        }

        loyaltyProgram.setPoints(loyaltyProgram.getPoints() - pointsToRedeem);
        loyaltyProgramRepository.save(loyaltyProgram);

        return true;
    }

    /**
     * Get loyalty program details for a guest.
     */
    public LoyaltyProgram getLoyaltyDetails(Integer guestId) {
        return loyaltyProgramRepository.findByGuestId(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Loyalty details for guest ID " + guestId + " not found."));
    }

    /**
     * Search loyalty programs based on filters.
     */
    public List<LoyaltyProgram> searchLoyaltyPrograms(LoyaltyProgramDto loyaltyProgramDto) {
        Specification<LoyaltyProgram> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (loyaltyProgramDto.getGuestId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("guestId"), loyaltyProgramDto.getGuestId()));
            }

            if (loyaltyProgramDto.getPoints() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("points"), loyaltyProgramDto.getPoints()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return loyaltyProgramRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    /**
     * Create or update a loyalty program.
     */
    public LoyaltyProgram saveOrUpdateLoyaltyProgram(LoyaltyProgramDto loyaltyProgramDto) {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
        loyaltyProgram.setGuestId(loyaltyProgramDto.getGuestId());
        loyaltyProgram.setPoints(loyaltyProgramDto.getPoints());
        return loyaltyProgramRepository.save(loyaltyProgram);
    }

    /**
     * Delete loyalty program by guest ID.
     */
    public void deleteLoyaltyProgram(Integer guestId) {
        LoyaltyProgram loyaltyProgram = loyaltyProgramRepository.findByGuestId(guestId)
                .orElseThrow(() -> new EntityNotFoundException("Loyalty details for guest ID " + guestId + " not found."));
        loyaltyProgramRepository.delete(loyaltyProgram);
    }

    private LoyaltyProgram createNewLoyaltyProgram(Integer guestId) {
        LoyaltyProgram loyaltyProgram = new LoyaltyProgram();
        loyaltyProgram.setGuestId(guestId);
        loyaltyProgram.setPoints(0);
        return loyaltyProgram;
    }

    private int calculatePoints(BigDecimal amountSpent) {
        // Example calculation: 1 point for every $10 spent
        BigDecimal points = amountSpent.divide(BigDecimal.valueOf(10), RoundingMode.FLOOR);
        return points.intValue();
    }
}
