package com.lodgitechpro.hotelmanagement.bookingManagement.controller;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.GuestDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Guest;
import com.lodgitechpro.hotelmanagement.bookingManagement.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping
    public ResponseEntity<Guest> createGuest(@RequestBody GuestDto guestDto) {
        Guest guest = guestService.saveAndUpdateGuest(guestDto);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/{guestId}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Integer guestId) {
        Guest guest = guestService.getGuestById(guestId);
        return ResponseEntity.ok(guest);
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        return ResponseEntity.ok(guests);
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Integer guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Guest> searchGuests(@RequestBody GuestDto guestDto) {
        return   guestService.searchGuests(guestDto);
    }
}
