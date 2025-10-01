package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.AssignmentDTO;
import com.campusConnect.classroomService.service.AssignmentService;
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
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AssignmentController {

    private final AssignmentService assignmentService;

    // Create assignment
    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.info("Request to create assignment: {} for classroom: {}",
                assignmentDTO.getTitle(), assignmentDTO.getClassroomId());

        try {
            AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
            return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating assignment: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all assignments
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        log.info("Request to get all assignments");

        try {
            List<AssignmentDTO> assignments = assignmentService.getAllAssignments();
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all assignments: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable Long id) {
        log.info("Request to get assignment with ID: {}", id);

        try {
            AssignmentDTO assignment = assignmentService.getAssignmentById(id);
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignment with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update assignment
    @PutMapping("/{id}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long id,
                                                          @Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.info("Request to update assignment with ID: {}", id);

        try {
            AssignmentDTO updatedAssignment = assignmentService.updateAssignment(id, assignmentDTO);
            return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating assignment with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete assignment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.info("Request to delete assignment with ID: {}", id);

        try {
            assignmentService.deleteAssignment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting assignment with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get assignments by classroom
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByClassroom(@PathVariable Long classroomId) {
        log.info("Request to get assignments for classroom: {}", classroomId);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByClassroom(classroomId);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by classroom {}: {}", classroomId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search assignments by title
    @GetMapping("/search/title")
    public ResponseEntity<List<AssignmentDTO>> searchAssignmentsByTitle(@RequestParam String title) {
        log.info("Request to search assignments by title: {}", title);

        try {
            List<AssignmentDTO> assignments = assignmentService.searchAssignmentsByTitle(title);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching assignments by title {}: {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by deadline before
    @GetMapping("/deadline/before")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByDeadlineBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        log.info("Request to get assignments with deadline before: {}", deadline);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByDeadlineBefore(deadline);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by deadline before: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by deadline after
    @GetMapping("/deadline/after")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByDeadlineAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        log.info("Request to get assignments with deadline after: {}", deadline);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByDeadlineAfter(deadline);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by deadline after: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by deadline between
    @GetMapping("/deadline/between")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByDeadlineBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Request to get assignments with deadline between: {} and {}", startDate, endDate);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByDeadlineBetween(startDate, endDate);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by deadline between dates: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by classroom and deadline before
    @GetMapping("/classroom/{classroomId}/deadline/before")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByClassroomAndDeadlineBefore(
            @PathVariable Long classroomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        log.info("Request to get assignments for classroom: {} with deadline before: {}", classroomId, deadline);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByClassroomAndDeadlineBefore(classroomId, deadline);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by classroom and deadline before: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by classroom and deadline after
    @GetMapping("/classroom/{classroomId}/deadline/after")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByClassroomAndDeadlineAfter(
            @PathVariable Long classroomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        log.info("Request to get assignments for classroom: {} with deadline after: {}", classroomId, deadline);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByClassroomAndDeadlineAfter(classroomId, deadline);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by classroom and deadline after: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments due today
    @GetMapping("/due/today")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsDueToday() {
        log.info("Request to get assignments due today");

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsDueToday();
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments due today: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments due this week
    @GetMapping("/due/this-week")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsDueThisWeek() {
        log.info("Request to get assignments due this week");

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsDueThisWeek();
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments due this week: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get overdue assignments
    @GetMapping("/overdue")
    public ResponseEntity<List<AssignmentDTO>> getOverdueAssignments() {
        log.info("Request to get overdue assignments");

        try {
            List<AssignmentDTO> assignments = assignmentService.getOverdueAssignments();
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching overdue assignments: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments due within hours
    @GetMapping("/due/within-hours/{hours}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsDueWithinHours(@PathVariable int hours) {
        log.info("Request to get assignments due within {} hours", hours);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsDueWithinHours(hours);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments due within hours: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsByTeacher(@PathVariable Long teacherId) {
        log.info("Request to get assignments for teacher: {}", teacherId);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsByTeacher(teacherId);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by teacher {}: {}", teacherId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsBySubject(@PathVariable String subject) {
        log.info("Request to get assignments for subject: {}", subject);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsBySubject(subject);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by subject {}: {}", subject, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get assignments by semester
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<AssignmentDTO>> getAssignmentsBySemester(@PathVariable Integer semester) {
        log.info("Request to get assignments for semester: {}", semester);

        try {
            List<AssignmentDTO> assignments = assignmentService.getAssignmentsBySemester(semester);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching assignments by semester {}: {}", semester, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get upcoming assignments
    @GetMapping("/upcoming/{days}")
    public ResponseEntity<List<AssignmentDTO>> getUpcomingAssignments(@PathVariable int days) {
        log.info("Request to get assignments due within {} days", days);

        try {
            List<AssignmentDTO> assignments = assignmentService.getUpcomingAssignments(days);
            return new ResponseEntity<>(assignments, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching upcoming assignments: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count assignments by classroom
    @GetMapping("/count/classroom/{classroomId}")
    public ResponseEntity<Long> countAssignmentsByClassroom(@PathVariable Long classroomId) {
        log.info("Request to count assignments for classroom: {}", classroomId);

        try {
            Long count = assignmentService.countAssignmentsByClassroom(classroomId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting assignments by classroom {}: {}", classroomId, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Extend deadline
    @PatchMapping("/{assignmentId}/extend-deadline")
    public ResponseEntity<AssignmentDTO> extendDeadline(
            @PathVariable Long assignmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newDeadline) {
        log.info("Request to extend deadline for assignment {} to {}", assignmentId, newDeadline);

        try {
            AssignmentDTO updatedAssignment = assignmentService.extendDeadline(assignmentId, newDeadline);
            return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error extending deadline for assignment {}: {}", assignmentId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
