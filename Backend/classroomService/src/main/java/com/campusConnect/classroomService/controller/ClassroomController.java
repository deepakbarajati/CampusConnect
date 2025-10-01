package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.ClassroomDTO;
import com.campusConnect.classroomService.service.ClassroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClassroomController {

    private final ClassroomService classroomService;

    // Create classroom
    @PostMapping
    public ResponseEntity<ClassroomDTO> createClassroom(@Valid @RequestBody ClassroomDTO classroomDTO) {
        log.info("Request to create classroom for teacher: {} with subject: {}",
                classroomDTO.getTeacherId(), classroomDTO.getSubject());

        try {
            ClassroomDTO createdClassroom = classroomService.createClassroom(classroomDTO);
            return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating classroom: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all classrooms
    @GetMapping
    public ResponseEntity<List<ClassroomDTO>> getAllClassrooms() {
        log.info("Request to get all classrooms");

        try {
            List<ClassroomDTO> classrooms = classroomService.getAllClassrooms();
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all classrooms: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classroom by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomDTO> getClassroomById(@PathVariable Long id) {
        log.info("Request to get classroom with ID: {}", id);

        try {
            ClassroomDTO classroom = classroomService.getClassroomById(id);
            return new ResponseEntity<>(classroom, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classroom with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update classroom
    @PutMapping("/{id}")
    public ResponseEntity<ClassroomDTO> updateClassroom(@PathVariable Long id,
                                                        @Valid @RequestBody ClassroomDTO classroomDTO) {
        log.info("Request to update classroom with ID: {}", id);

        try {
            ClassroomDTO updatedClassroom = classroomService.updateClassroom(id, classroomDTO);
            return new ResponseEntity<>(updatedClassroom, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating classroom with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete classroom
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassroom(@PathVariable Long id) {
        log.info("Request to delete classroom with ID: {}", id);

        try {
            classroomService.deleteClassroom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting classroom with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get classrooms by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsByTeacher(@PathVariable Long teacherId) {
        log.info("Request to get classrooms for teacher: {}", teacherId);

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsByTeacher(teacherId);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms by teacher {}: {}", teacherId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classrooms by semester
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsBySemester(@PathVariable Integer semester) {
        log.info("Request to get classrooms for semester: {}", semester);

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsBySemester(semester);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms by semester {}: {}", semester, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search classrooms by subject
    @GetMapping("/search/subject")
    public ResponseEntity<List<ClassroomDTO>> searchClassroomsBySubject(@RequestParam String subject) {
        log.info("Request to search classrooms by subject: {}", subject);

        try {
            List<ClassroomDTO> classrooms = classroomService.searchClassroomsBySubject(subject);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching classrooms by subject {}: {}", subject, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classrooms by teacher and semester
    @GetMapping("/teacher/{teacherId}/semester/{semester}")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsByTeacherAndSemester(
            @PathVariable Long teacherId,
            @PathVariable Integer semester) {
        log.info("Request to get classrooms for teacher: {} and semester: {}", teacherId, semester);

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsByTeacherAndSemester(teacherId, semester);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms by teacher and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classrooms with schedule
    @GetMapping("/with-schedule")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsWithSchedule() {
        log.info("Request to get classrooms with schedule");

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsWithSchedule();
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms with schedule: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classrooms without schedule
    @GetMapping("/without-schedule")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsWithoutSchedule() {
        log.info("Request to get classrooms without schedule");

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsWithoutSchedule();
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms without schedule: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get classrooms by subject and semester
    @GetMapping("/search")
    public ResponseEntity<List<ClassroomDTO>> getClassroomsBySubjectAndSemester(
            @RequestParam String subject,
            @RequestParam Integer semester) {
        log.info("Request to get classrooms for subject: {} and semester: {}", subject, semester);

        try {
            List<ClassroomDTO> classrooms = classroomService.getClassroomsBySubjectAndSemester(subject, semester);
            return new ResponseEntity<>(classrooms, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching classrooms by subject and semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Assign schedule to classroom
    @PostMapping("/{classroomId}/schedule/{scheduleId}")
    public ResponseEntity<ClassroomDTO> assignSchedule(@PathVariable Long classroomId,
                                                       @PathVariable Long scheduleId) {
        log.info("Request to assign schedule {} to classroom {}", scheduleId, classroomId);

        try {
            ClassroomDTO updatedClassroom = classroomService.assignSchedule(classroomId, scheduleId);
            return new ResponseEntity<>(updatedClassroom, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error assigning schedule {} to classroom {}: {}", scheduleId, classroomId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Remove schedule from classroom
    @DeleteMapping("/{classroomId}/schedule")
    public ResponseEntity<ClassroomDTO> removeSchedule(@PathVariable Long classroomId) {
        log.info("Request to remove schedule from classroom {}", classroomId);

        try {
            ClassroomDTO updatedClassroom = classroomService.removeSchedule(classroomId);
            return new ResponseEntity<>(updatedClassroom, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error removing schedule from classroom {}: {}", classroomId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
