package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.ExamDTO;
import com.campusConnect.classroomService.entity.enums.ExamType;
import com.campusConnect.classroomService.service.ExamService;
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
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ExamController {

    private final ExamService examService;

    // Create exam
    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@Valid @RequestBody ExamDTO examDTO) {
        log.info("Request to create exam for classroom: {} with type: {} on date: {}",
                examDTO.getClassroomId(), examDTO.getType(), examDTO.getDate());

        try {
            ExamDTO createdExam = examService.createExam(examDTO);
            return new ResponseEntity<>(createdExam, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating exam: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all exams
    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        log.info("Request to get all exams");

        try {
            List<ExamDTO> exams = examService.getAllExams();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all exams: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exam by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        log.info("Request to get exam with ID: {}", id);

        try {
            ExamDTO exam = examService.getExamById(id);
            return new ResponseEntity<>(exam, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exam with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update exam
    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable Long id,
                                              @Valid @RequestBody ExamDTO examDTO) {
        log.info("Request to update exam with ID: {}", id);

        try {
            ExamDTO updatedExam = examService.updateExam(id, examDTO);
            return new ResponseEntity<>(updatedExam, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating exam with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete exam
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        log.info("Request to delete exam with ID: {}", id);

        try {
            examService.deleteExam(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting exam with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get exams by classroom
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<ExamDTO>> getExamsByClassroom(@PathVariable Long classroomId) {
        log.info("Request to get exams for classroom: {}", classroomId);

        try {
            List<ExamDTO> exams = examService.getExamsByClassroom(classroomId);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by classroom {}: {}", classroomId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ExamDTO>> getExamsByType(@PathVariable ExamType type) {
        log.info("Request to get exams by type: {}", type);

        try {
            List<ExamDTO> exams = examService.getExamsByType(type);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by type {}: {}", type, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExamDTO>> getExamsByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Request to get exams for date: {}", date);

        try {
            List<ExamDTO> exams = examService.getExamsByDate(date);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by date {}: {}", date, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by classroom and type
    @GetMapping("/classroom/{classroomId}/type/{type}")
    public ResponseEntity<List<ExamDTO>> getExamsByClassroomAndType(
            @PathVariable Long classroomId,
            @PathVariable ExamType type) {
        log.info("Request to get exams for classroom: {} with type: {}", classroomId, type);

        try {
            List<ExamDTO> exams = examService.getExamsByClassroomAndType(classroomId, type);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by classroom and type: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by classroom and date
    @GetMapping("/classroom/{classroomId}/date/{date}")
    public ResponseEntity<List<ExamDTO>> getExamsByClassroomAndDate(
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Request to get exams for classroom: {} on date: {}", classroomId, date);

        try {
            List<ExamDTO> exams = examService.getExamsByClassroomAndDate(classroomId, date);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by classroom and date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by type and date
    @GetMapping("/type/{type}/date/{date}")
    public ResponseEntity<List<ExamDTO>> getExamsByTypeAndDate(
            @PathVariable ExamType type,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Request to get exams with type: {} on date: {}", type, date);

        try {
            List<ExamDTO> exams = examService.getExamsByTypeAndDate(type, date);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by type and date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by date range
    @GetMapping("/date-range")
    public ResponseEntity<List<ExamDTO>> getExamsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        log.info("Request to get exams between {} and {}", startDate, endDate);

        try {
            List<ExamDTO> exams = examService.getExamsByDateRange(startDate, endDate);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by date range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by classroom and date range
    @GetMapping("/classroom/{classroomId}/date-range")
    public ResponseEntity<List<ExamDTO>> getExamsByClassroomAndDateRange(
            @PathVariable Long classroomId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        log.info("Request to get exams for classroom: {} between {} and {}", classroomId, startDate, endDate);

        try {
            List<ExamDTO> exams = examService.getExamsByClassroomAndDateRange(classroomId, startDate, endDate);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by classroom and date range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by marks range
    @GetMapping("/marks-range")
    public ResponseEntity<List<ExamDTO>> getExamsByMarksRange(
            @RequestParam Float minMarks,
            @RequestParam Float maxMarks) {
        log.info("Request to get exams with marks between {} and {}", minMarks, maxMarks);

        try {
            List<ExamDTO> exams = examService.getExamsByMarksRange(minMarks, maxMarks);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by marks range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by classroom and marks range
    @GetMapping("/classroom/{classroomId}/marks-range")
    public ResponseEntity<List<ExamDTO>> getExamsByClassroomAndMarksRange(
            @PathVariable Long classroomId,
            @RequestParam Float minMarks,
            @RequestParam Float maxMarks) {
        log.info("Request to get exams for classroom: {} with marks between {} and {}", classroomId, minMarks, maxMarks);

        try {
            List<ExamDTO> exams = examService.getExamsByClassroomAndMarksRange(classroomId, minMarks, maxMarks);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by classroom and marks range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams before date
    @GetMapping("/before/{date}")
    public ResponseEntity<List<ExamDTO>> getExamsByDateBefore(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Request to get exams before date: {}", date);

        try {
            List<ExamDTO> exams = examService.getExamsByDateBefore(date);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams before date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams after date
    @GetMapping("/after/{date}")
    public ResponseEntity<List<ExamDTO>> getExamsByDateAfter(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        log.info("Request to get exams after date: {}", date);

        try {
            List<ExamDTO> exams = examService.getExamsByDateAfter(date);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams after date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ExamDTO>> getExamsByTeacher(@PathVariable Long teacherId) {
        log.info("Request to get exams for teacher: {}", teacherId);

        try {
            List<ExamDTO> exams = examService.getExamsByTeacher(teacherId);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by teacher: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<ExamDTO>> getExamsBySubject(@PathVariable String subject) {
        log.info("Request to get exams for subject: {}", subject);

        try {
            List<ExamDTO> exams = examService.getExamsBySubject(subject);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by subject: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams by semester
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<ExamDTO>> getExamsBySemester(@PathVariable Integer semester) {
        log.info("Request to get exams for semester: {}", semester);

        try {
            List<ExamDTO> exams = examService.getExamsBySemester(semester);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams by semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams with marks above threshold
    @GetMapping("/marks-above/{threshold}")
    public ResponseEntity<List<ExamDTO>> getExamsWithMarksAbove(@PathVariable Float threshold) {
        log.info("Request to get exams with marks above: {}", threshold);

        try {
            List<ExamDTO> exams = examService.getExamsWithMarksAbove(threshold);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams with marks above threshold: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams with marks below threshold
    @GetMapping("/marks-below/{threshold}")
    public ResponseEntity<List<ExamDTO>> getExamsWithMarksBelow(@PathVariable Float threshold) {
        log.info("Request to get exams with marks below: {}", threshold);

        try {
            List<ExamDTO> exams = examService.getExamsWithMarksBelow(threshold);
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams with marks below threshold: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get upcoming exams
    @GetMapping("/upcoming")
    public ResponseEntity<List<ExamDTO>> getUpcomingExams() {
        log.info("Request to get upcoming exams");

        try {
            List<ExamDTO> exams = examService.getUpcomingExams();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching upcoming exams: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get past exams
    @GetMapping("/past")
    public ResponseEntity<List<ExamDTO>> getPastExams() {
        log.info("Request to get past exams");

        try {
            List<ExamDTO> exams = examService.getPastExams();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching past exams: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams for today
    @GetMapping("/today")
    public ResponseEntity<List<ExamDTO>> getExamsForToday() {
        log.info("Request to get exams for today");

        try {
            List<ExamDTO> exams = examService.getExamsForToday();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams for today: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams for week
    @GetMapping("/this-week")
    public ResponseEntity<List<ExamDTO>> getExamsForWeek() {
        log.info("Request to get exams for this week");

        try {
            List<ExamDTO> exams = examService.getExamsForWeek();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams for this week: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get exams for month
    @GetMapping("/this-month")
    public ResponseEntity<List<ExamDTO>> getExamsForMonth() {
        log.info("Request to get exams for this month");

        try {
            List<ExamDTO> exams = examService.getExamsForMonth();
            return new ResponseEntity<>(exams, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching exams for this month: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate average marks by classroom
    @GetMapping("/classroom/{classroomId}/average-marks")
    public ResponseEntity<Double> calculateAverageMarksByClassroom(@PathVariable Long classroomId) {
        log.info("Request to calculate average marks for classroom: {}", classroomId);

        try {
            Double average = examService.calculateAverageMarksByClassroom(classroomId);
            return new ResponseEntity<>(average, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating average marks by classroom: {}", e.getMessage());
            return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate average marks by type
    @GetMapping("/type/{type}/average-marks")
    public ResponseEntity<Double> calculateAverageMarksByType(@PathVariable ExamType type) {
        log.info("Request to calculate average marks for exam type: {}", type);

        try {
            Double average = examService.calculateAverageMarksByType(type);
            return new ResponseEntity<>(average, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating average marks by type: {}", e.getMessage());
            return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count exams by classroom
    @GetMapping("/count/classroom/{classroomId}")
    public ResponseEntity<Long> countExamsByClassroom(@PathVariable Long classroomId) {
        log.info("Request to count exams for classroom: {}", classroomId);

        try {
            Long count = examService.countExamsByClassroom(classroomId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting exams by classroom: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count exams by type
    @GetMapping("/count/type/{type}")
    public ResponseEntity<Long> countExamsByType(@PathVariable ExamType type) {
        log.info("Request to count exams by type: {}", type);

        try {
            Long count = examService.countExamsByType(type);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting exams by type: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
