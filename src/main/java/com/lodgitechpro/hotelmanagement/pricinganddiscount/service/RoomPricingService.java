package com.lodgitechpro.hotelmanagement.pricinganddiscount.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomType;
import com.lodgitechpro.hotelmanagement.exception.EntityNotFoundException;
import com.lodgitechpro.hotelmanagement.mapper.EntityMapper;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.DailyPricesDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.DailyRoomPriceDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.dto.RoomPricingDto;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.entity.RoomPricing;
import com.lodgitechpro.hotelmanagement.pricinganddiscount.repository.RoomPricingRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomPricingService {

    private final RoomPricingRepository roomPricingRepository;
    private final EntityMapper entityMapper;

    public DailyRoomPriceDto calculatePrice(RoomType roomType, Date checkIn, Date checkOut) {
        // Fetch all pricing records for the room type within the given range
        List<RoomPricing> roomPricings = roomPricingRepository.findAll().stream()
                .filter(roomPricing -> roomPricing.getRoomType() == roomType)
                .filter(RoomPricing::getActive)
                .toList();

        List<RoomPricing> roomPricingWithDates = roomPricings.stream()
                .filter(pricing -> pricing.getStartDate() != null && pricing.getEndDate() != null)
                .toList();

        List<RoomPricing> roomPricingWithoutDates = roomPricings.stream()
                .filter(pricing -> pricing.getStartDate() == null && pricing.getEndDate() == null)
                .toList();

        if (roomPricings.isEmpty()) {
            throw new EntityNotFoundException("No pricing found for room type " + roomType + " within the given date range.");
        }

        double totalPrice = 0;
        List<DailyPricesDto> dailyPricesDtoList = new ArrayList<>();

        // Iterate over each day in the booking range
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkIn);

        while (!calendar.getTime().after(checkOut)) {
            Date currentDay = calendar.getTime();

            RoomPricing applicablePricing = null;
            if (!roomPricingWithDates.isEmpty()) {
                Optional<RoomPricing> priceWithDate = roomPricingWithDates.stream()
                        .filter(pricing -> !currentDay.before(pricing.getStartDate()) &&
                                !currentDay.after(pricing.getEndDate()))
                        .findFirst();

                if (priceWithDate.isPresent()) {
                    applicablePricing = priceWithDate.get();
                }
            }

            if (applicablePricing == null && !roomPricingWithoutDates.isEmpty()) {
                applicablePricing = roomPricingWithoutDates.get(0);
            }

            if (applicablePricing == null) {
                throw new IllegalStateException("No applicable pricing found for date " + currentDay);
            }

            // Add the price for the day
            double totalAmount = calculateDailyPrice(applicablePricing);
            totalPrice += totalAmount;
            DailyPricesDto dailyPricesDto = DailyPricesDto.builder()
                    .date(currentDay)
                    .price(totalAmount)
                    .build();
            dailyPricesDtoList.add(dailyPricesDto);
            // Move to the next day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        log.info("Calculated price for room {} from {} to {} is {}", roomType, checkIn, checkOut, totalPrice);
        return DailyRoomPriceDto.builder()
                .dailyPricesDtoList(dailyPricesDtoList)
                .totalPrice(totalPrice)
                .build();
    }

    private double calculateDailyPrice(RoomPricing roomPricing) {
        BigDecimal basePrice = roomPricing.getBasePrice();
        BigDecimal seasonalMultiplier = roomPricing.getSeasonalMultiplier();
        BigDecimal demandMultiplier = roomPricing.getDemandMultiplier();

        // Multiply using BigDecimal's multiply method
        BigDecimal totalPrice = basePrice
                .multiply(seasonalMultiplier)
                .multiply(demandMultiplier);

        // Convert BigDecimal to double for return
        return totalPrice.doubleValue();
    }

    /**
     * Retrieve seasonal pricing for a specific date range.
     *
     * @param date Date range to filter the seasonal pricing.
     * @return List of room pricing for the specified date range.
     */
    public List<RoomPricing> getSeasonalPricing(Date date) {
        // Example logic (Assuming the repository can query based on date)
        List<RoomPricing> allPricing = roomPricingRepository.findAll();

        return allPricing.stream()
                .filter(pricing -> pricing.getStartDate() != null && pricing.getEndDate() != null &&
                        !pricing.getStartDate().after(date) && !pricing.getEndDate().before(date))
                .collect(Collectors.toList());
    }

    /**
     * Update the base price for a specific room type.
     *
     * @param roomType the room type.
     * @param newPrice   New base price to update.
     * @return Updated RoomPricing entity.
     */
    public RoomPricing updateBasePrice(RoomType roomType, BigDecimal newPrice) {
        RoomPricing roomPricing = roomPricingRepository.findByRoomType(roomType)
                .orElseThrow(() -> new EntityNotFoundException("Room Pricing with roomType " + roomType + " not found."));

        roomPricing.setBasePrice(newPrice);

        RoomPricing updatedRoomPricing = roomPricingRepository.save(roomPricing);

        log.info("Updated base price for room {} to {}", roomType, newPrice);
        return updatedRoomPricing;
    }

    public List<RoomPricing> getActiveRoomPricings() {
        return roomPricingRepository.findAll().stream()
                .filter(RoomPricing::getActive)
                .collect(Collectors.toList());
    }

    public List<RoomPricing> getAllRoomPricings() {
        return roomPricingRepository.findAll();
    }

    public RoomPricing saveAndUpdateRoomPricing(RoomPricingDto roomPricingDto) {
        RoomPricing roomPricing = entityMapper.mapDtoToEntity(roomPricingDto, RoomPricing.class);

        roomPricing = roomPricingRepository.save(roomPricing);
        return roomPricing;
    }

    public RoomPricing getRoomPricingById(Integer roomPricingId) {
        return roomPricingRepository.findById(roomPricingId)
                .orElseThrow(() -> new EntityNotFoundException("RoomPricing with ID " + roomPricingId + " not found."));
    }

    public RoomPricing deactivateRoomPricing(Integer roomPricingId) {
        RoomPricing roomPricing = roomPricingRepository.findById(roomPricingId)
                .orElseThrow(() -> new EntityNotFoundException("RoomPricing with ID " + roomPricingId + " not found."));
        if(roomPricing.getActive()) {
            roomPricing.setActive(false);
            roomPricingRepository.save(roomPricing);
        }
        return roomPricing;
    }

    public RoomPricing activateRoomPricing(Integer roomPricingId) {
        RoomPricing roomPricing = roomPricingRepository.findById(roomPricingId)
                .orElseThrow(() -> new EntityNotFoundException("RoomPricing with ID " + roomPricingId + " not found."));
        if(!roomPricing.getActive()) {
            roomPricing.setActive(true);
            roomPricingRepository.save(roomPricing);
        }
        return roomPricing;
    }

    public List<RoomPricing> searchRoomPricing(RoomPricingDto searchDto) {
        Specification<RoomPricing> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by ID
            if (searchDto.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), searchDto.getId()));
            }

            // Filter by Room Type
            if (searchDto.getRoomType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomType"), searchDto.getRoomType()));
            }

            // Filter by Active Status
            if (searchDto.getActive() != null) {
                predicates.add(criteriaBuilder.equal(root.get("active"), searchDto.getActive()));
            }

            // Filter by Date Range
            if (searchDto.getStartDate() != null && searchDto.getEndDate() != null) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), searchDto.getStartDate()),
                        criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), searchDto.getEndDate())
                ));
            }

            // Filter by Base Price Range
            if (searchDto.getMinBasePrice() != null && searchDto.getMaxBasePrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("basePrice"),
                        searchDto.getMinBasePrice(), searchDto.getMaxBasePrice()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Execute the query
        return roomPricingRepository.findAll(specification, Pageable.unpaged()).getContent();
    }
}
