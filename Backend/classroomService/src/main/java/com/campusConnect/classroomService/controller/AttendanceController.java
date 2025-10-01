package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.AttendanceDTO;
import com.campusConnect.classroomService.entity.enums.AttendanceStatus;
import com.campusConnect.classroomService.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Mark attendance
    @PostMapping
    public ResponseEntity<AttendanceDTO> markAttendance(@Valid @RequestBody AttendanceDTO attendanceDTO) {
        log.info("Request to mark attendance for student: {} in classroom: {} on date: {} with status: {}",
                attendanceDTO.getStudentId(), attendanceDTO.getClassroomId(),
                attendanceDTO.getDate(), attendanceDTO.getStatus());

        try {
            AttendanceDTO markedAttendance = attendanceService.markAttendance(attendanceDTO);
            return new ResponseEntity<>(markedAttendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking attendance: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all attendance
    @GetMapping
    public ResponseEntity<List<AttendanceDTO>> getAllAttendance() {
        log.info("Request to get all attendance records");

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAllAttendance();
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all attendance: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by ID
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDTO> getAttendanceById(@PathVariable Long id) {
        log.info("Request to get attendance with ID: {}", id);

        try {
            AttendanceDTO attendance = attendanceService.getAttendanceById(id);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update attendance
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceDTO> updateAttendance(@PathVariable Long id,
                                                          @Valid @RequestBody AttendanceDTO attendanceDTO) {
        log.info("Request to update attendance with ID: {}", id);

        try {
            AttendanceDTO updatedAttendance = attendanceService.updateAttendance(id, attendanceDTO);
            return new ResponseEntity<>(updatedAttendance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating attendance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete attendance
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.info("Request to delete attendance with ID: {}", id);

        try {
            attendanceService.deleteAttendance(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting attendance with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get attendance by student and date
    @GetMapping("/student/{studentId}/date/{date}")
    public ResponseEntity<AttendanceDTO> getAttendanceByStudentAndDate(
            @PathVariable Long studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get attendance for student: {} on date: {}", studentId, date);

        try {
            AttendanceDTO attendance = attendanceService.getAttendanceByStudentAndDate(studentId, date);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by student and date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get attendance by student, classroom and date
    @GetMapping("/student/{studentId}/classroom/{classroomId}/date/{date}")
    public ResponseEntity<AttendanceDTO> getAttendanceByStudentAndClassroomAndDate(
            @PathVariable Long studentId,
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get attendance for student: {} in classroom: {} on date: {}", studentId, classroomId, date);

        try {
            AttendanceDTO attendance = attendanceService.getAttendanceByStudentAndClassroomAndDate(studentId, classroomId, date);
            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by student, classroom and date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get attendance by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudent(@PathVariable Long studentId) {
        log.info("Request to get attendance for student: {}", studentId);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByStudent(studentId);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by student {}: {}", studentId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by classroom
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassroom(@PathVariable Long classroomId) {
        log.info("Request to get attendance for classroom: {}", classroomId);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByClassroom(classroomId);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by classroom {}: {}", classroomId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get attendance for date: {}", date);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByDate(date);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by date {}: {}", date, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by classroom and date
    @GetMapping("/classroom/{classroomId}/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassroomAndDate(
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get attendance for classroom: {} on date: {}", classroomId, date);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByClassroomAndDate(classroomId, date);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by classroom and date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by student and date range
    @GetMapping("/student/{studentId}/date-range")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudentAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Request to get attendance for student: {} between {} and {}", studentId, startDate, endDate);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByStudentAndDateRange(studentId, startDate, endDate);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by student and date range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by classroom and date range
    @GetMapping("/classroom/{classroomId}/date-range")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassroomAndDateRange(
            @PathVariable Long classroomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("Request to get attendance for classroom: {} between {} and {}", classroomId, startDate, endDate);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByClassroomAndDateRange(classroomId, startDate, endDate);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by classroom and date range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStatus(@PathVariable AttendanceStatus status) {
        log.info("Request to get attendance by status: {}", status);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByStatus(status);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by status: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by classroom and status
    @GetMapping("/classroom/{classroomId}/status/{status}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByClassroomAndStatus(
            @PathVariable Long classroomId,
            @PathVariable AttendanceStatus status) {
        log.info("Request to get attendance for classroom: {} with status: {}", classroomId, status);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByClassroomAndStatus(classroomId, status);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by classroom and status: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by student and status
    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByStudentAndStatus(
            @PathVariable Long studentId,
            @PathVariable AttendanceStatus status) {
        log.info("Request to get attendance for student: {} with status: {}", studentId, status);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByStudentAndStatus(studentId, status);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by student and status: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by marked by
    @GetMapping("/marked-by/{markedBy}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByMarkedBy(@PathVariable Long markedBy) {
        log.info("Request to get attendance marked by: {}", markedBy);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByMarkedBy(markedBy);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by marked by: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get attendance by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByTeacher(@PathVariable Long teacherId) {
        log.info("Request to get attendance for teacher: {}", teacherId);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByTeacher(teacherId);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching attendance by teacher: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get absent students by date
    @GetMapping("/absent/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getAbsentStudentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get absent students for date: {}", date);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAbsentStudentsByDate(date);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching absent students by date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get excused students by date
    @GetMapping("/excused/date/{date}")
    public ResponseEntity<List<AttendanceDTO>> getExcusedStudentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to get excused students for date: {}", date);

        try {
            List<AttendanceDTO> attendanceList = attendanceService.getExcusedStudentsByDate(date);
            return new ResponseEntity<>(attendanceList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching excused students by date: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate attendance percentage by student
    @GetMapping("/student/{studentId}/percentage")
    public ResponseEntity<Double> calculateAttendancePercentageByStudent(@PathVariable Long studentId) {
        log.info("Request to calculate attendance percentage for student: {}", studentId);

        try {
            Double percentage = attendanceService.calculateAttendancePercentageByStudent(studentId);
            return new ResponseEntity<>(percentage, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating attendance percentage: {}", e.getMessage());
            return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count attendance by student and status
    @GetMapping("/count/student/{studentId}/status/{status}")
    public ResponseEntity<Long> countAttendanceByStudentAndStatus(
            @PathVariable Long studentId,
            @PathVariable AttendanceStatus status) {
        log.info("Request to count attendance for student: {} with status: {}", studentId, status);

        try {
            Long count = attendanceService.countAttendanceByStudentAndStatus(studentId, status);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting attendance by student and status: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count attendance by classroom and status
    @GetMapping("/count/classroom/{classroomId}/status/{status}")
    public ResponseEntity<Long> countAttendanceByClassroomAndStatus(
            @PathVariable Long classroomId,
            @PathVariable AttendanceStatus status) {
        log.info("Request to count attendance for classroom: {} with status: {}", classroomId, status);

        try {
            Long count = attendanceService.countAttendanceByClassroomAndStatus(classroomId, status);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting attendance by classroom and status: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Find students with low attendance
    @GetMapping("/classroom/{classroomId}/low-attendance")
    public ResponseEntity<List<Long>> findStudentsWithLowAttendance(
            @PathVariable Long classroomId,
            @RequestParam Double percentage) {
        log.info("Request to find students with low attendance (< {}%) in classroom: {}", percentage, classroomId);

        try {
            List<Long> studentIds = attendanceService.findStudentsWithLowAttendance(classroomId, percentage);
            return new ResponseEntity<>(studentIds, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error finding students with low attendance: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark bulk attendance
    @PostMapping("/bulk")
    public ResponseEntity<List<AttendanceDTO>> markBulkAttendance(@Valid @RequestBody List<AttendanceDTO> attendanceList) {
        log.info("Request to mark bulk attendance for {} records", attendanceList.size());

        try {
            List<AttendanceDTO> markedAttendance = attendanceService.markBulkAttendance(attendanceList);
            return new ResponseEntity<>(markedAttendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking bulk attendance: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Mark class attendance
    @PostMapping("/classroom/{classroomId}/date/{date}/mark-class")
    public ResponseEntity<List<AttendanceDTO>> markClassAttendance(
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam List<Long> presentStudentIds,
            @RequestParam List<Long> absentStudentIds,
            @RequestParam List<Long> excusedStudentIds,
            @RequestParam(required = false) Long markedBy) {
        log.info("Request to mark class attendance for classroom: {} on date: {}", classroomId, date);

        try {
            List<AttendanceDTO> markedAttendance = attendanceService.markClassAttendance(
                    classroomId, date, presentStudentIds, absentStudentIds, excusedStudentIds, markedBy);
            return new ResponseEntity<>(markedAttendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking class attendance: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Mark student present
    @PostMapping("/student/{studentId}/classroom/{classroomId}/date/{date}/present")
    public ResponseEntity<AttendanceDTO> markStudentPresent(
            @PathVariable Long studentId,
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long markedBy) {
        log.info("Request to mark student {} as present in classroom {} on {}", studentId, classroomId, date);

        try {
            AttendanceDTO attendance = attendanceService.markStudentPresent(studentId, classroomId, date, markedBy);
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking student present: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Mark student absent
    @PostMapping("/student/{studentId}/classroom/{classroomId}/date/{date}/absent")
    public ResponseEntity<AttendanceDTO> markStudentAbsent(
            @PathVariable Long studentId,
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long markedBy) {
        log.info("Request to mark student {} as absent in classroom {} on {}", studentId, classroomId, date);

        try {
            AttendanceDTO attendance = attendanceService.markStudentAbsent(studentId, classroomId, date, markedBy);
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking student absent: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Mark student excused
    @PostMapping("/student/{studentId}/classroom/{classroomId}/date/{date}/excused")
    public ResponseEntity<AttendanceDTO> markStudentExcused(
            @PathVariable Long studentId,
            @PathVariable Long classroomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long markedBy) {
        log.info("Request to mark student {} as excused in classroom {} on {}", studentId, classroomId, date);

        try {
            AttendanceDTO attendance = attendanceService.markStudentExcused(studentId, classroomId, date, markedBy);
            return new ResponseEntity<>(attendance, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error marking student excused: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Check if student is present on date
    @GetMapping("/student/{studentId}/date/{date}/is-present")
    public ResponseEntity<Boolean> isStudentPresentOnDate(
            @PathVariable Long studentId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Request to check if student {} is present on {}", studentId, date);

        try {
            boolean isPresent = attendanceService.isStudentPresentOnDate(studentId, date);
            return new ResponseEntity<>(isPresent, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking student presence: {}", e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
