package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.ClassroomScheduleDTO;
import com.campusConnect.classroomService.dto.DayDTO;
import com.campusConnect.classroomService.entity.ClassroomSchedule;
import com.campusConnect.classroomService.repository.ClassroomScheduleRepository;
import com.campusConnect.classroomService.service.ClassroomScheduleService;
import com.campusConnect.classroomService.util.Day;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClassroomScheduleServiceImpl implements ClassroomScheduleService {

    private final ClassroomScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassroomScheduleDTO createSchedule(ClassroomScheduleDTO scheduleDTO) {
        log.info("Creating new classroom schedule with {} day(s)", scheduleDTO.getDay().size());

        ClassroomSchedule schedule = new ClassroomSchedule();
        schedule.setDay(scheduleDTO.getDay().stream()
                .map(dayDTO -> modelMapper.map(dayDTO, Day.class))
                .collect(Collectors.toList()));

        ClassroomSchedule savedSchedule = scheduleRepository.save(schedule);
        log.info("Classroom schedule created successfully with ID: {}", savedSchedule.getId());

        return mapToDTO(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomScheduleDTO getScheduleById(Long id) {
        log.info("Fetching classroom schedule with ID: {}", id);

        ClassroomSchedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        return mapToDTO(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getAllSchedules() {
        log.info("Fetching all classroom schedules");

        List<ClassroomSchedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomScheduleDTO updateSchedule(Long id, ClassroomScheduleDTO scheduleDTO) {
        log.info("Updating classroom schedule with ID: {}", id);

        ClassroomSchedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));

        existingSchedule.setDay(scheduleDTO.getDay().stream()
                .map(dayDTO -> modelMapper.map(dayDTO, Day.class))
                .collect(Collectors.toList()));

        ClassroomSchedule updatedSchedule = scheduleRepository.save(existingSchedule);
        log.info("Classroom schedule updated successfully with ID: {}", updatedSchedule.getId());

        return mapToDTO(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long id) {
        log.info("Deleting classroom schedule with ID: {}", id);

        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }

        scheduleRepository.deleteById(id);
        log.info("Classroom schedule deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesByDayOfWeek(DayOfWeek dayOfWeek) {
        log.info("Fetching schedules for day of week: {}", dayOfWeek);

        List<ClassroomSchedule> schedules = scheduleRepository.findByDayOfWeek(dayOfWeek);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesByDayOfWeekAndTimeRange(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
        log.info("Fetching schedules for day: {} and time range: {} - {}", dayOfWeek, startTime, endTime);

        List<ClassroomSchedule> schedules = scheduleRepository.findByDayOfWeekAndTimeRange(dayOfWeek, startTime, endTime);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesStartingBefore(DayOfWeek dayOfWeek, Time time) {
        log.info("Fetching schedules starting before {} on {}", time, dayOfWeek);

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesStartingBefore(dayOfWeek, time);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesEndingAfter(DayOfWeek dayOfWeek, Time time) {
        log.info("Fetching schedules ending after {} on {}", time, dayOfWeek);

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesEndingAfter(dayOfWeek, time);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesWithMultipleDays() {
        log.info("Fetching schedules with multiple days");

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesWithMultipleDays();
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesWithSingleDay() {
        log.info("Fetching schedules with single day");

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesWithSingleDay();
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesByDayCount(int dayCount) {
        log.info("Fetching schedules with {} days", dayCount);

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesByDayCount(dayCount);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getWeekdaySchedules() {
        log.info("Fetching weekday schedules");

        List<DayOfWeek> weekdays = Arrays.asList(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

        List<ClassroomSchedule> schedules = scheduleRepository.findWeekdaySchedules(weekdays);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getWeekendSchedules() {
        log.info("Fetching weekend schedules");

        List<DayOfWeek> weekends = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        List<ClassroomSchedule> schedules = scheduleRepository.findWeekendSchedules(weekends);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getConflictingSchedules(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
        log.info("Fetching conflicting schedules for day: {} and time: {} - {}", dayOfWeek, startTime, endTime);

        List<ClassroomSchedule> schedules = scheduleRepository.findConflictingSchedules(dayOfWeek, startTime, endTime);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomScheduleDTO> getSchedulesByMinDuration(int minDurationMinutes) {
        log.info("Fetching schedules with minimum duration of {} minutes", minDurationMinutes);

        List<ClassroomSchedule> schedules = scheduleRepository.findSchedulesByMinDuration(minDurationMinutes);
        return schedules.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomScheduleDTO addDayToSchedule(Long scheduleId, DayDTO dayDTO) {
        log.info("Adding day {} {} - {} to schedule {}", dayDTO.getDay(), dayDTO.getStartTime(), dayDTO.getEndTime(), scheduleId);

        ClassroomSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        if (schedule.getDay() == null) {
            schedule.setDay(new ArrayList<>());
        }

        Day newDay = modelMapper.map(dayDTO, Day.class);
        schedule.getDay().add(newDay);

        schedule = scheduleRepository.save(schedule);
        return mapToDTO(schedule);
    }

    @Override
    public ClassroomScheduleDTO removeDayFromSchedule(Long scheduleId, DayOfWeek dayOfWeek, Time startTime) {
        log.info("Removing day {} starting at {} from schedule {}", dayOfWeek, startTime, scheduleId);

        ClassroomSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        if (schedule.getDay() != null) {
            schedule.getDay().removeIf(day ->
                    day.getDay().equals(dayOfWeek) && day.getStartTime().equals(startTime));
            schedule = scheduleRepository.save(schedule);
        }

        return mapToDTO(schedule);
    }

    @Override
    public ClassroomScheduleDTO addMultipleDaysToSchedule(Long scheduleId, List<DayDTO> days) {
        log.info("Adding {} days to schedule {}", days.size(), scheduleId);

        ClassroomSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        if (schedule.getDay() == null) {
            schedule.setDay(new ArrayList<>());
        }

        List<Day> newDays = days.stream()
                .map(dayDTO -> modelMapper.map(dayDTO, Day.class))
                .collect(Collectors.toList());

        schedule.getDay().addAll(newDays);
        schedule = scheduleRepository.save(schedule);

        return mapToDTO(schedule);
    }

    @Override
    public ClassroomScheduleDTO updateDayInSchedule(Long scheduleId, DayOfWeek oldDayOfWeek, Time oldStartTime, DayDTO newDayDTO) {
        log.info("Updating day {} {} in schedule {} to {} {} - {}",
                oldDayOfWeek, oldStartTime, scheduleId,
                newDayDTO.getDay(), newDayDTO.getStartTime(), newDayDTO.getEndTime());

        ClassroomSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        if (schedule.getDay() != null) {
            for (Day day : schedule.getDay()) {
                if (day.getDay().equals(oldDayOfWeek) && day.getStartTime().equals(oldStartTime)) {
                    day.setDay(newDayDTO.getDay());
                    day.setStartTime(newDayDTO.getStartTime());
                    day.setEndTime(newDayDTO.getEndTime());
                    break;
                }
            }
            schedule = scheduleRepository.save(schedule);
        }

        return mapToDTO(schedule);
    }

    private ClassroomScheduleDTO mapToDTO(ClassroomSchedule schedule) {
        ClassroomScheduleDTO dto = new ClassroomScheduleDTO();
        dto.setId(schedule.getId());

        if (schedule.getDay() != null) {
            List<DayDTO> dayDTOs = schedule.getDay().stream()
                    .map(day -> modelMapper.map(day, DayDTO.class))
                    .collect(Collectors.toList());
            dto.setDay(dayDTOs);
        }

        return dto;
    }
}
