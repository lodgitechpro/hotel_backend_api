package com.lodgitechpro.hotelmanagement.bookingManagement.controller;

import com.lodgitechpro.hotelmanagement.bookingManagement.dtos.RoomsDto;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Rooms;
import com.lodgitechpro.hotelmanagement.bookingManagement.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomsController {

    @Autowired
    private RoomsService roomService;

    @PostMapping
    public ResponseEntity<Rooms> createRoom(@RequestBody RoomsDto roomDto) {
        Rooms room = roomService.saveAndUpdateRooms(roomDto);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Rooms> getRoomById(@PathVariable Integer roomId) {
        Rooms room = roomService.getRoomById(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping
    public ResponseEntity<List<Rooms>> getRooms() {
        List<Rooms> room = roomService.getAllRooms();
        return ResponseEntity.ok(room);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Rooms>> getAvailableRooms(
            @RequestParam Integer locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkIn,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOut) {
        List<Rooms> availableRooms = roomService.getAvailableRooms(locationId, checkIn, checkOut);
        return ResponseEntity.ok(availableRooms);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Rooms>> searchRooms(@RequestBody RoomsDto roomDto) {
        List<Rooms> rooms = roomService.searchRooms(roomDto);
        return ResponseEntity.ok(rooms);
    }
}
