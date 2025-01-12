package com.lodgitechpro.hotelmanagement.pricinganddiscount.service;

import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.exception.PromotionNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.PromotionDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.Promotions;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.repository.PromotionRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final EntityMapper entityMapper;

    /**
     * Create a new promotion.
     */
    public Promotions createPromotion(PromotionDto promotionDto) {
        Promotions promotion = entityMapper.mapDtoToEntity(promotionDto, Promotions.class);
        promotion = promotionRepository.save(promotion);
        return promotion;
    }

    /**
     * Validate a promotion code based on the booking date.
     */
    public Promotions validatePromotionCode(String promotionCode, LocalDate bookingDate) {
        return promotionRepository.findByPromotionCodeAndActive(promotionCode, true)
                .filter(promotion -> (promotion.getValidFrom() == null || !bookingDate.isBefore(promotion.getValidFrom())) &&
                        (promotion.getValidTo() == null || !bookingDate.isAfter(promotion.getValidTo())))
                .orElseThrow(() -> new PromotionNotFoundException(promotionCode, "Invalid or expired promotion code."));
    }

    /**
     * Get all available promotions.
     */
    public List<Promotions> getAvailablePromotions() {
        return promotionRepository.findByActive(true);
    }

    /**
     * Get a promotion by ID.
     */
    public Promotions getPromotionById(Integer promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(() -> new EntityNotFoundException("Promotion with ID " + promotionId + " not found."));
    }

    /**
     * Update an existing promotion or create a new one if the ID is not provided.
     */
    public Promotions saveAndUpdatePromotion(PromotionDto promotionDto) {
        Promotions promotion = entityMapper.mapDtoToEntity(promotionDto, Promotions.class);
        promotion = promotionRepository.save(promotion);
        return promotion;
    }

    /**
     * Delete a promotion by ID.
     */
    public void deletePromotion(Integer promotionId) {
        if (!promotionRepository.existsById(promotionId)) {
            throw new EntityNotFoundException("Promotion with ID " + promotionId + " not found.");
        }
        promotionRepository.deleteById(promotionId);
    }

    /**
     * Search promotions dynamically using PromotionDto filters.
     */
    public List<Promotions> searchPromotions(PromotionDto promotionDto) {
        Specification<Promotions> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (promotionDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), promotionDto.getId()));
            }

            if (StringUtils.isNotBlank(promotionDto.getPromotionCode())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("promotionCode")),
                        "%" + promotionDto.getPromotionCode().toLowerCase() + "%"));
            }

            if (promotionDto.getDiscountPercentage() != null) {
                predicates.add(criteriaBuilder.equal(root.get("discountPercentage"), promotionDto.getDiscountPercentage()));
            }

            if (promotionDto.getValidFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("validFrom"), promotionDto.getValidFrom()));
            }

            if (promotionDto.getValidTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("validTo"), promotionDto.getValidTo()));
            }

            if (promotionDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), promotionDto.getActive()));
            }

            if (promotionDto.getApplicableRoomTypes() != null && !promotionDto.getApplicableRoomTypes().isEmpty()) {
                Predicate roomTypesPredicate = criteriaBuilder.or(
                        promotionDto.getApplicableRoomTypes().stream()
                                .map(roomType -> criteriaBuilder.like(root.get("applicableRoomTypes"), "%" + roomType + "%"))
                                .toArray(Predicate[]::new)
                );
                predicates.add(roomTypesPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return promotionRepository.findAll(specification, Pageable.unpaged()).getContent();
    }

    /**
     * Deactivate promotions by their IDs.
     * If a promotion with a given ID does not exist, it will throw an exception.
     */
    public List<Promotions> deactivatePromotions(List<Integer> promotionIds) {
        List<Promotions> promotions = promotionRepository.findAllById(promotionIds);

        if (promotions.isEmpty()) {
            throw new EntityNotFoundException("No promotions found for the given IDs.");
        }

        promotions.forEach(promotion -> {
            if (Boolean.TRUE.equals(promotion.getActive())) {
                promotion.setActive(false);
            }
        });

        return promotionRepository.saveAll(promotions);
    }

    /**
     * Activate promotions by their IDs.
     * If a promotion with a given ID does not exist, it will throw an exception.
     */
    public List<Promotions> activatePromotions(List<Integer> promotionIds) {
        List<Promotions> promotions = promotionRepository.findAllById(promotionIds);

        if (promotions.isEmpty()) {
            throw new EntityNotFoundException("No promotions found for the given IDs.");
        }

        promotions.forEach(promotion -> {
            if (Boolean.FALSE.equals(promotion.getActive())) {
                promotion.setActive(true);
            }
        });

        return promotionRepository.saveAll(promotions);
    }
}