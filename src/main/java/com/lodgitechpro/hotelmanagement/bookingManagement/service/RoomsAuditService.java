package com.lodgitechpro.hotelmanagement.bookingManagement.service;

import com.lodgitechpro.hotelmanagement.bookingManagement.entity.Rooms;
import com.lodgitechpro.hotelmanagement.bookingManagement.entity.RoomsAudit;
import com.lodgitechpro.hotelmanagement.bookingManagement.enums.RoomStatus;
import com.lodgitechpro.hotelmanagement.bookingManagement.repository.RoomsAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomsAuditService {

    private final RoomsAuditRepository roomAuditRepository;

    /**
     * Fetches the audit logs for a specific room.
     *
     * @param roomId the ID of the room
     * @return List of RoomAudit logs
     */
    @Transactional(readOnly = true)
    public List<RoomsAudit> getRoomAuditLogs(Integer roomId) {
        return roomAuditRepository.findByRoomId(roomId);
    }

    /**
     * Logs a room audit entry.
     *
     * @param room           the Room entity being audited
     * @param changeType     the type of change (e.g., "UPDATE", "CREATE", "DELETE")
     * @param previousStatus the previous status of the room (if applicable)
     * @param changedBy      the user or system responsible for the change
     */
    @Transactional
    public void logRoomAudit(Rooms room, RoomStatus previousStatus, String changedBy) {
        RoomsAudit roomAudit = new RoomsAudit();
        roomAudit.setRoomId(room.getId());
        roomAudit.setPreviousStatus(previousStatus);
        roomAudit.setStatus(room.getStatus());
        roomAudit.setChangedBy(changedBy);
        roomAudit.setChangedAt(LocalDateTime.now());
        roomAuditRepository.save(roomAudit);
    }
}
