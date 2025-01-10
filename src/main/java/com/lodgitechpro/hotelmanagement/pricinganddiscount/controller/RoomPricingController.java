package com.lodgitechpro.hotelmanagement.pricinganddiscount.controller;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.DailyRoomPriceDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.RoomPricingDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.RoomPricing;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.service.RoomPricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/room-pricing")
@RequiredArgsConstructor
@Slf4j
public class RoomPricingController {

    private final RoomPricingService roomPricingService;

    @GetMapping("/calculate-price")
    public ResponseEntity<DailyRoomPriceDto> calculatePrice(
            @RequestParam RoomType roomType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOut) {
        DailyRoomPriceDto price = roomPricingService.calculatePrice(roomType, checkIn, checkOut);
        return ResponseEntity.ok(price);
    }

    @GetMapping("/seasonal-pricing")
    public ResponseEntity<List<RoomPricing>> getSeasonalPricing(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        List<RoomPricing> seasonalPricing = roomPricingService.getSeasonalPricing(date);
        return ResponseEntity.ok(seasonalPricing);
    }

    @PutMapping("/update-base-price")
    public ResponseEntity<RoomPricing> updateBasePrice(
            @RequestParam RoomType roomType,
            @RequestParam BigDecimal newPrice) {
        RoomPricing updatedPricing = roomPricingService.updateBasePrice(roomType, newPrice);
        return ResponseEntity.ok(updatedPricing);
    }

    @GetMapping("/active")
    public ResponseEntity<List<RoomPricing>> getActiveRoomPricings() {
        List<RoomPricing> activePricings = roomPricingService.getActiveRoomPricings();
        return ResponseEntity.ok(activePricings);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomPricing>> getAllRoomPricings() {
        List<RoomPricing> allPricings = roomPricingService.getAllRoomPricings();
        return ResponseEntity.ok(allPricings);
    }

    @PostMapping
    public ResponseEntity<RoomPricing> saveOrUpdateRoomPricing(@RequestBody RoomPricingDto roomPricingDto) {
        RoomPricing savedPricing = roomPricingService.saveAndUpdateRoomPricing(roomPricingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPricing);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomPricing> getRoomPricingById(@PathVariable Integer id) {
        RoomPricing roomPricing = roomPricingService.getRoomPricingById(id);
        return ResponseEntity.ok(roomPricing);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<RoomPricing> deactivateRoomPricing(@PathVariable Integer id) {
        RoomPricing deactivatedPricing = roomPricingService.deactivateRoomPricing(id);
        return ResponseEntity.ok(deactivatedPricing);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<RoomPricing> activateRoomPricing(@PathVariable Integer id) {
        RoomPricing activatedPricing = roomPricingService.activateRoomPricing(id);
        return ResponseEntity.ok(activatedPricing);
    }

    @PostMapping("/search")
    public ResponseEntity<List<RoomPricing>> searchRoomPricing(@RequestBody RoomPricingDto searchDto) {
        List<RoomPricing> roomPricings = roomPricingService.searchRoomPricing(searchDto);
        return ResponseEntity.ok(roomPricings);
    }
}
