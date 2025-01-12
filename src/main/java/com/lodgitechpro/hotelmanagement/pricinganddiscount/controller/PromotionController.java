package com.lodgitechpro.hotelmanagement.pricinganddiscount.controller;

import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.PromotionDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.Promotions;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Slf4j
public class PromotionController {

    private final PromotionService promotionService;

    /**
     * Create a new promotion.
     */
    @PostMapping
    public ResponseEntity<Promotions> createPromotion(@RequestBody PromotionDto promotionDto) {
        Promotions createdPromotion = promotionService.createPromotion(promotionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
    }

    /**
     * Validate a promotion code based on the booking date.
     */
    @GetMapping("/validate")
    public ResponseEntity<Promotions> validatePromotionCode(
            @RequestParam String promotionCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate) {
        Promotions validatedPromotion = promotionService.validatePromotionCode(promotionCode, bookingDate);
        return ResponseEntity.ok(validatedPromotion);
    }

    /**
     * Get all active promotions.
     */
    @GetMapping
    public ResponseEntity<List<Promotions>> getAvailablePromotions() {
        List<Promotions> promotions = promotionService.getAvailablePromotions();
        return ResponseEntity.ok(promotions);
    }

    /**
     * Get a promotion by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Promotions> getPromotionById(@PathVariable Integer id) {
        Promotions promotion = promotionService.getPromotionById(id);
        return ResponseEntity.ok(promotion);
    }

    /**
     * Update an existing promotion or create a new one if the ID is not provided.
     */
    @PutMapping
    public ResponseEntity<Promotions> saveOrUpdatePromotion(@RequestBody PromotionDto promotionDto) {
        Promotions updatedPromotion = promotionService.saveAndUpdatePromotion(promotionDto);
        return ResponseEntity.ok(updatedPromotion);
    }

    /**
     * Delete a promotion by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Integer id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search promotions using dynamic filters.
     */
    @PostMapping("/search")
    public ResponseEntity<List<Promotions>> searchPromotions(@RequestBody PromotionDto promotionDto) {
        List<Promotions> promotions = promotionService.searchPromotions(promotionDto);
        return ResponseEntity.ok(promotions);
    }

    /**
     * Deactivate promotions by their IDs.
     */
    @PostMapping("/deactivate")
    public ResponseEntity<List<Promotions>> deactivatePromotions(@RequestBody List<Integer> promotionIds) {
        List<Promotions> deactivatedPromotions = promotionService.deactivatePromotions(promotionIds);
        return ResponseEntity.ok(deactivatedPromotions);
    }

    /**
     * Activate promotions by their IDs.
     */
    @PostMapping("/activate")
    public ResponseEntity<List<Promotions>> activatePromotions(@RequestBody List<Integer> promotionIds) {
        List<Promotions> activatedPromotions = promotionService.activatePromotions(promotionIds);
        return ResponseEntity.ok(activatedPromotions);
    }
}
