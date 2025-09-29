package com.campusConnect.aluminiService.controller;

import com.campusConnect.aluminiService.dto.MentorshipDTO;
import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import com.campusConnect.aluminiService.service.MentorshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/mentorships")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MentorshipController {

    private final MentorshipService mentorshipService;

    // Create mentorship
    @PostMapping
    public ResponseEntity<MentorshipDTO> createMentorship(@Valid @RequestBody MentorshipDTO mentorshipDTO) {
        log.info("Request to create mentorship between mentor {} and mentee {}",
                mentorshipDTO.getMentorId(), mentorshipDTO.getMenteeId());

        try {
            MentorshipDTO createdMentorship = mentorshipService.createMentorship(mentorshipDTO);
            return new ResponseEntity<>(createdMentorship, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating mentorship: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all mentorships
    @GetMapping
    public ResponseEntity<List<MentorshipDTO>> getAllMentorships() {
        log.info("Request to get all mentorships");

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getAllMentorships();
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all mentorships: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorship by ID
    @GetMapping("/{id}")
    public ResponseEntity<MentorshipDTO> getMentorshipById(@PathVariable Long id) {
        log.info("Request to get mentorship with ID: {}", id);

        try {
            MentorshipDTO mentorship = mentorshipService.getMentorshipById(id);
            return new ResponseEntity<>(mentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorship with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update mentorship
    @PutMapping("/{id}")
    public ResponseEntity<MentorshipDTO> updateMentorship(@PathVariable Long id,
                                                          @Valid @RequestBody MentorshipDTO mentorshipDTO) {
        log.info("Request to update mentorship with ID: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.updateMentorship(id, mentorshipDTO);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating mentorship with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete mentorship
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentorship(@PathVariable Long id) {
        log.info("Request to delete mentorship with ID: {}", id);

        try {
            mentorshipService.deleteMentorship(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting mentorship with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get mentorships by mentor
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByMentor(@PathVariable Long mentorId) {
        log.info("Request to get mentorships for mentor: {}", mentorId);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByMentor(mentorId);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by mentor {}: {}", mentorId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships by mentee
    @GetMapping("/mentee/{menteeId}")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByMentee(@PathVariable Long menteeId) {
        log.info("Request to get mentorships for mentee: {}", menteeId);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByMentee(menteeId);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by mentee {}: {}", menteeId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByStatus(@PathVariable MentorShipStatus status) {
        log.info("Request to get mentorships with status: {}", status);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByStatus(status);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by status {}: {}", status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships by meeting frequency
    @GetMapping("/meeting-frequency/{frequency}")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByMeetingFrequency(@PathVariable String frequency) {
        log.info("Request to get mentorships with meeting frequency: {}", frequency);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByMeetingFrequency(frequency);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by meeting frequency {}: {}", frequency, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships by meeting mode
    @GetMapping("/meeting-mode/{mode}")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByMeetingMode(@PathVariable String mode) {
        log.info("Request to get mentorships with meeting mode: {}", mode);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByMeetingMode(mode);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by meeting mode {}: {}", mode, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships between specific users
    @GetMapping("/between")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsBetweenUsers(
            @RequestParam Long mentorId,
            @RequestParam Long menteeId) {
        log.info("Request to get mentorships between mentor {} and mentee {}", mentorId, menteeId);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsBetweenUsers(mentorId, menteeId);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships between users: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get active mentorships by mentor
    @GetMapping("/mentor/{mentorId}/active")
    public ResponseEntity<List<MentorshipDTO>> getActiveMentorshipsByMentor(@PathVariable Long mentorId) {
        log.info("Request to get active mentorships for mentor: {}", mentorId);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getActiveMentorshipsByMentor(mentorId);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching active mentorships by mentor {}: {}", mentorId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get active mentorships by mentee
    @GetMapping("/mentee/{menteeId}/active")
    public ResponseEntity<List<MentorshipDTO>> getActiveMentorshipsByMentee(@PathVariable Long menteeId) {
        log.info("Request to get active mentorships for mentee: {}", menteeId);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getActiveMentorshipsByMentee(menteeId);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching active mentorships by mentee {}: {}", menteeId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships ending soon
    @GetMapping("/ending-soon")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsEndingSoon(
            @RequestParam(defaultValue = "30") int days) {
        log.info("Request to get mentorships ending within {} days", days);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsEndingSoon(days);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships ending soon: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        log.info("Request to get mentorships between {} and {}", startDate, endDate);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsByDateRange(startDate, endDate);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships by date range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get mentorships with high ratings
    @GetMapping("/high-ratings")
    public ResponseEntity<List<MentorshipDTO>> getMentorshipsWithHighRatings(
            @RequestParam(defaultValue = "4.0") Double minRating) {
        log.info("Request to get mentorships with rating >= {}", minRating);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.getMentorshipsWithHighRatings(minRating);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching mentorships with high ratings: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search mentorships by title
    @GetMapping("/search")
    public ResponseEntity<List<MentorshipDTO>> searchMentorshipsByTitle(@RequestParam String title) {
        log.info("Request to search mentorships with title: {}", title);

        try {
            List<MentorshipDTO> mentorships = mentorshipService.searchMentorshipsByTitle(title);
            return new ResponseEntity<>(mentorships, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching mentorships by title {}: {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update mentorship status
    @PatchMapping("/{id}/status")
    public ResponseEntity<MentorshipDTO> updateMentorshipStatus(@PathVariable Long id,
                                                                @RequestParam MentorShipStatus status) {
        log.info("Request to update status of mentorship {} to {}", id, status);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.updateMentorshipStatus(id, status);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating status of mentorship {} to {}: {}", id, status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Accept mentorship request
    @PostMapping("/{id}/accept")
    public ResponseEntity<MentorshipDTO> acceptMentorshipRequest(@PathVariable Long id) {
        log.info("Request to accept mentorship: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.acceptMentorshipRequest(id);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error accepting mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Complete mentorship
    @PostMapping("/{id}/complete")
    public ResponseEntity<MentorshipDTO> completeMentorship(@PathVariable Long id) {
        log.info("Request to complete mentorship: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.completeMentorship(id);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error completing mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Pause mentorship
    @PostMapping("/{id}/pause")
    public ResponseEntity<MentorshipDTO> pauseMentorship(@PathVariable Long id) {
        log.info("Request to pause mentorship: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.pauseMentorship(id);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error pausing mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Resume mentorship
    @PostMapping("/{id}/resume")
    public ResponseEntity<MentorshipDTO> resumeMentorship(@PathVariable Long id) {
        log.info("Request to resume mentorship: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.resumeMentorship(id);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error resuming mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Add feedback rating
    @PostMapping("/{id}/feedback")
    public ResponseEntity<MentorshipDTO> addFeedbackRating(@PathVariable Long id,
                                                           @RequestParam Double rating) {
        log.info("Request to add feedback rating {} to mentorship: {}", rating, id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.addFeedbackRating(id, rating);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding feedback rating to mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Increment session count
    @PostMapping("/{id}/session")
    public ResponseEntity<MentorshipDTO> incrementSessionCount(@PathVariable Long id) {
        log.info("Request to increment session count for mentorship: {}", id);

        try {
            MentorshipDTO updatedMentorship = mentorshipService.incrementSessionCount(id);
            return new ResponseEntity<>(updatedMentorship, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error incrementing session count for mentorship {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Count mentorships by mentor
    @GetMapping("/count/mentor/{mentorId}")
    public ResponseEntity<Long> countMentorshipsByMentor(@PathVariable Long mentorId) {
        log.info("Request to count mentorships by mentor: {}", mentorId);

        try {
            Long count = mentorshipService.countMentorshipsByMentor(mentorId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting mentorships by mentor {}: {}", mentorId, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count mentorships by mentee
    @GetMapping("/count/mentee/{menteeId}")
    public ResponseEntity<Long> countMentorshipsByMentee(@PathVariable Long menteeId) {
        log.info("Request to count mentorships by mentee: {}", menteeId);

        try {
            Long count = mentorshipService.countMentorshipsByMentee(menteeId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting mentorships by mentee {}: {}", menteeId, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count mentorships by status
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> countMentorshipsByStatus(@PathVariable MentorShipStatus status) {
        log.info("Request to count mentorships with status: {}", status);

        try {
            Long count = mentorshipService.countMentorshipsByStatus(status);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting mentorships by status {}: {}", status, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if mentorship is active
    @GetMapping("/is-active")
    public ResponseEntity<Boolean> isMentorshipActive(@RequestParam Long mentorId,
                                                      @RequestParam Long menteeId) {
        log.info("Request to check if mentorship is active between mentor {} and mentee {}", mentorId, menteeId);

        try {
            boolean isActive = mentorshipService.isMentorshipActive(mentorId, menteeId);
            return new ResponseEntity<>(isActive, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking mentorship status: {}", e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Expire mentorships
    @PostMapping("/expire")
    public ResponseEntity<String> expireMentorships() {
        log.info("Request to expire mentorships");

        try {
            mentorshipService.expireMentorships();
            return new ResponseEntity<>("Expired mentorships have been completed", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error expiring mentorships: {}", e.getMessage());
            return new ResponseEntity<>("Error expiring mentorships", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
