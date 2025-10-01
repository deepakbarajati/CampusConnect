package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.ClassroomScheduleDTO;
import com.campusConnect.classroomService.dto.DayDTO;
import com.campusConnect.classroomService.service.ClassroomScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/classroom-schedules")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ClassroomScheduleController {

    private final ClassroomScheduleService scheduleService;

    // Create schedule
    @PostMapping
    public ResponseEntity<ClassroomScheduleDTO> createSchedule(@Valid @RequestBody ClassroomScheduleDTO scheduleDTO) {
        log.info("Request to create classroom schedule with {} day(s)", scheduleDTO.getDay().size());

        try {
            ClassroomScheduleDTO createdSchedule = scheduleService.createSchedule(scheduleDTO);
            return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating schedule: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<ClassroomScheduleDTO>> getAllSchedules() {
        log.info("Request to get all classroom schedules");

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getAllSchedules();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all schedules: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassroomScheduleDTO> getScheduleById(@PathVariable Long id) {
        log.info("Request to get schedule with ID: {}", id);

        try {
            ClassroomScheduleDTO schedule = scheduleService.getScheduleById(id);
            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedule with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update schedule
    @PutMapping("/{id}")
    public ResponseEntity<ClassroomScheduleDTO> updateSchedule(@PathVariable Long id,
                                                               @Valid @RequestBody ClassroomScheduleDTO scheduleDTO) {
        log.info("Request to update schedule with ID: {}", id);

        try {
            ClassroomScheduleDTO updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating schedule with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete schedule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.info("Request to delete schedule with ID: {}", id);

        try {
            scheduleService.deleteSchedule(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting schedule with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get schedules by day of week
    @GetMapping("/day-of-week/{dayOfWeek}")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesByDayOfWeek(@PathVariable DayOfWeek dayOfWeek) {
        log.info("Request to get schedules for day of week: {}", dayOfWeek);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesByDayOfWeek(dayOfWeek);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules by day of week {}: {}", dayOfWeek, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules by day of week and time range
    @GetMapping("/day-of-week/{dayOfWeek}/time-range")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesByDayOfWeekAndTimeRange(
            @PathVariable DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time startTime,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time endTime) {
        log.info("Request to get schedules for day: {} and time range: {} - {}", dayOfWeek, startTime, endTime);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesByDayOfWeekAndTimeRange(dayOfWeek, startTime, endTime);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules by day and time range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules starting before specific time
    @GetMapping("/day-of-week/{dayOfWeek}/starting-before")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesStartingBefore(
            @PathVariable DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time time) {
        log.info("Request to get schedules starting before {} on {}", time, dayOfWeek);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesStartingBefore(dayOfWeek, time);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules starting before time: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules ending after specific time
    @GetMapping("/day-of-week/{dayOfWeek}/ending-after")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesEndingAfter(
            @PathVariable DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time time) {
        log.info("Request to get schedules ending after {} on {}", time, dayOfWeek);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesEndingAfter(dayOfWeek, time);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules ending after time: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules with multiple days
    @GetMapping("/multiple-days")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesWithMultipleDays() {
        log.info("Request to get schedules with multiple days");

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesWithMultipleDays();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules with multiple days: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules with single day
    @GetMapping("/single-day")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesWithSingleDay() {
        log.info("Request to get schedules with single day");

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesWithSingleDay();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules with single day: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules by day count
    @GetMapping("/day-count/{count}")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesByDayCount(@PathVariable int count) {
        log.info("Request to get schedules with {} days", count);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesByDayCount(count);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules with {} days: {}", count, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get weekday schedules
    @GetMapping("/weekdays")
    public ResponseEntity<List<ClassroomScheduleDTO>> getWeekdaySchedules() {
        log.info("Request to get weekday schedules");

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getWeekdaySchedules();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching weekday schedules: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get weekend schedules
    @GetMapping("/weekends")
    public ResponseEntity<List<ClassroomScheduleDTO>> getWeekendSchedules() {
        log.info("Request to get weekend schedules");

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getWeekendSchedules();
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching weekend schedules: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get conflicting schedules
    @GetMapping("/conflicts")
    public ResponseEntity<List<ClassroomScheduleDTO>> getConflictingSchedules(
            @RequestParam DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time startTime,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time endTime) {
        log.info("Request to get conflicting schedules for day: {} and time: {} - {}", dayOfWeek, startTime, endTime);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getConflictingSchedules(dayOfWeek, startTime, endTime);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching conflicting schedules: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules by minimum duration
    @GetMapping("/min-duration/{minutes}")
    public ResponseEntity<List<ClassroomScheduleDTO>> getSchedulesByMinDuration(@PathVariable int minutes) {
        log.info("Request to get schedules with minimum duration of {} minutes", minutes);

        try {
            List<ClassroomScheduleDTO> schedules = scheduleService.getSchedulesByMinDuration(minutes);
            return new ResponseEntity<>(schedules, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching schedules by minimum duration: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Add day to schedule
    @PostMapping("/{scheduleId}/days")
    public ResponseEntity<ClassroomScheduleDTO> addDayToSchedule(@PathVariable Long scheduleId,
                                                                 @Valid @RequestBody DayDTO dayDTO) {
        log.info("Request to add day {} {} - {} to schedule {}",
                dayDTO.getDay(), dayDTO.getStartTime(), dayDTO.getEndTime(), scheduleId);

        try {
            ClassroomScheduleDTO updatedSchedule = scheduleService.addDayToSchedule(scheduleId, dayDTO);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding day to schedule {}: {}", scheduleId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Remove day from schedule
    @DeleteMapping("/{scheduleId}/days")
    public ResponseEntity<ClassroomScheduleDTO> removeDayFromSchedule(
            @PathVariable Long scheduleId,
            @RequestParam DayOfWeek dayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time startTime) {
        log.info("Request to remove day {} starting at {} from schedule {}", dayOfWeek, startTime, scheduleId);

        try {
            ClassroomScheduleDTO updatedSchedule = scheduleService.removeDayFromSchedule(scheduleId, dayOfWeek, startTime);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error removing day from schedule {}: {}", scheduleId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Add multiple days to schedule
    @PostMapping("/{scheduleId}/days/bulk")
    public ResponseEntity<ClassroomScheduleDTO> addMultipleDaysToSchedule(@PathVariable Long scheduleId,
                                                                          @Valid @RequestBody List<DayDTO> days) {
        log.info("Request to add {} days to schedule {}", days.size(), scheduleId);

        try {
            ClassroomScheduleDTO updatedSchedule = scheduleService.addMultipleDaysToSchedule(scheduleId, days);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding multiple days to schedule {}: {}", scheduleId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update day in schedule
    @PutMapping("/{scheduleId}/days")
    public ResponseEntity<ClassroomScheduleDTO> updateDayInSchedule(
            @PathVariable Long scheduleId,
            @RequestParam DayOfWeek oldDayOfWeek,
            @RequestParam @DateTimeFormat(pattern = "HH:mm:ss") Time oldStartTime,
            @Valid @RequestBody DayDTO newDayDTO) {
        log.info("Request to update day {} {} in schedule {} to {} {} - {}",
                oldDayOfWeek, oldStartTime, scheduleId,
                newDayDTO.getDay(), newDayDTO.getStartTime(), newDayDTO.getEndTime());

        try {
            ClassroomScheduleDTO updatedSchedule = scheduleService.updateDayInSchedule(scheduleId, oldDayOfWeek, oldStartTime, newDayDTO);
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating day in schedule {}: {}", scheduleId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
