package com.campusConnect.aluminiService.controller;

import com.campusConnect.aluminiService.dto.AlumniMeetDTO;
import com.campusConnect.aluminiService.nodes.enums.Mode;
import com.campusConnect.aluminiService.service.AlumniMeetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alumni-meets")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AlumniMeetController {

    private final AlumniMeetService alumniMeetService;

    // Create alumni meet
    @PostMapping
    public ResponseEntity<AlumniMeetDTO> createAlumniMeet(@Valid @RequestBody AlumniMeetDTO alumniMeetDTO) {
        log.info("Request to create alumni meet: {}", alumniMeetDTO.getTitle());

        try {
            AlumniMeetDTO createdMeet = alumniMeetService.createAlumniMeet(alumniMeetDTO);
            return new ResponseEntity<>(createdMeet, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating alumni meet: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all alumni meets
    @GetMapping
    public ResponseEntity<List<AlumniMeetDTO>> getAllAlumniMeets() {
        log.info("Request to get all alumni meets");

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getAllAlumniMeets();
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all alumni meets: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get alumni meet by ID
    @GetMapping("/{id}")
    public ResponseEntity<AlumniMeetDTO> getAlumniMeetById(@PathVariable Long id) {
        log.info("Request to get alumni meet with ID: {}", id);

        try {
            AlumniMeetDTO meet = alumniMeetService.getAlumniMeetById(id);
            return new ResponseEntity<>(meet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching alumni meet with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update alumni meet
    @PutMapping("/{id}")
    public ResponseEntity<AlumniMeetDTO> updateAlumniMeet(@PathVariable Long id,
                                                          @Valid @RequestBody AlumniMeetDTO alumniMeetDTO) {
        log.info("Request to update alumni meet with ID: {}", id);

        try {
            AlumniMeetDTO updatedMeet = alumniMeetService.updateAlumniMeet(id, alumniMeetDTO);
            return new ResponseEntity<>(updatedMeet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating alumni meet with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete alumni meet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumniMeet(@PathVariable Long id) {
        log.info("Request to delete alumni meet with ID: {}", id);

        try {
            alumniMeetService.deleteAlumniMeet(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting alumni meet with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get meets by organizer
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsByOrganizer(@PathVariable Long organizerId) {
        log.info("Request to get meets organized by: {}", organizerId);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsByOrganizer(organizerId);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets by organizer {}: {}", organizerId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get meets by mode
    @GetMapping("/mode/{mode}")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsByMode(@PathVariable Mode mode) {
        log.info("Request to get meets with mode: {}", mode);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsByMode(mode);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets by mode {}: {}", mode, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get meets by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsByStatus(@PathVariable String status) {
        log.info("Request to get meets with status: {}", status);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsByStatus(status);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets by status {}: {}", status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get meets by participant
    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsByParticipant(@PathVariable Long participantId) {
        log.info("Request to get meets for participant: {}", participantId);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsByParticipant(participantId);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets by participant {}: {}", participantId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get upcoming meets
    @GetMapping("/upcoming")
    public ResponseEntity<List<AlumniMeetDTO>> getUpcomingMeets() {
        log.info("Request to get upcoming meets");

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getUpcomingMeets();
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching upcoming meets: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get meets between dates
    @GetMapping("/between")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Request to get meets between {} and {}", startDate, endDate);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsBetweenDates(startDate, endDate);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets between dates: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search meets by title
    @GetMapping("/search")
    public ResponseEntity<List<AlumniMeetDTO>> searchMeetsByTitle(@RequestParam String title) {
        log.info("Request to search meets with title: {}", title);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.searchMeetsByTitle(title);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching meets by title {}: {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get meets by tag
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<AlumniMeetDTO>> getMeetsByTag(@PathVariable String tag) {
        log.info("Request to get meets with tag: {}", tag);

        try {
            List<AlumniMeetDTO> meets = alumniMeetService.getMeetsByTag(tag);
            return new ResponseEntity<>(meets, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching meets by tag {}: {}", tag, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add participant to meet
    @PostMapping("/{meetId}/participants/{participantId}")
    public ResponseEntity<AlumniMeetDTO> addParticipant(@PathVariable Long meetId,
                                                        @PathVariable Long participantId) {
        log.info("Request to add participant {} to meet {}", participantId, meetId);

        try {
            AlumniMeetDTO updatedMeet = alumniMeetService.addParticipant(meetId, participantId);
            return new ResponseEntity<>(updatedMeet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding participant {} to meet {}: {}", participantId, meetId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Remove participant from meet
    @DeleteMapping("/{meetId}/participants/{participantId}")
    public ResponseEntity<AlumniMeetDTO> removeParticipant(@PathVariable Long meetId,
                                                           @PathVariable Long participantId) {
        log.info("Request to remove participant {} from meet {}", participantId, meetId);

        try {
            AlumniMeetDTO updatedMeet = alumniMeetService.removeParticipant(meetId, participantId);
            return new ResponseEntity<>(updatedMeet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error removing participant {} from meet {}: {}", participantId, meetId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get participant count
    @GetMapping("/{meetId}/participants/count")
    public ResponseEntity<Integer> getParticipantCount(@PathVariable Long meetId) {
        log.info("Request to get participant count for meet: {}", meetId);

        try {
            Integer count = alumniMeetService.getParticipantCount(meetId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting participant count for meet {}: {}", meetId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update meet status
    @PatchMapping("/{meetId}/status")
    public ResponseEntity<AlumniMeetDTO> updateMeetStatus(@PathVariable Long meetId,
                                                          @RequestParam String status) {
        log.info("Request to update status of meet {} to {}", meetId, status);

        try {
            AlumniMeetDTO updatedMeet = alumniMeetService.updateMeetStatus(meetId, status);
            return new ResponseEntity<>(updatedMeet, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating status of meet {} to {}: {}", meetId, status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Check if meet is full
    @GetMapping("/{meetId}/is-full")
    public ResponseEntity<Boolean> isMeetFull(@PathVariable Long meetId) {
        log.info("Request to check if meet {} is full", meetId);

        try {
            boolean isFull = alumniMeetService.isMeetFull(meetId);
            return new ResponseEntity<>(isFull, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking if meet {} is full: {}", meetId, e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    // Check if user is participant
    @GetMapping("/{meetId}/participants/{userId}/exists")
    public ResponseEntity<Boolean> isUserParticipant(@PathVariable Long meetId,
                                                     @PathVariable Long userId) {
        log.info("Request to check if user {} is participant in meet {}", userId, meetId);

        try {
            boolean isParticipant = alumniMeetService.isUserParticipant(meetId, userId);
            return new ResponseEntity<>(isParticipant, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking if user {} is participant in meet {}: {}", userId, meetId, e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
