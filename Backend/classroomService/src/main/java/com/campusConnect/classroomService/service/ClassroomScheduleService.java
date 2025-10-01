package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.ClassroomScheduleDTO;
import com.campusConnect.classroomService.dto.DayDTO;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

public interface ClassroomScheduleService {

    // CRUD Operations
    ClassroomScheduleDTO createSchedule(ClassroomScheduleDTO scheduleDTO);
    ClassroomScheduleDTO getScheduleById(Long id);
    List<ClassroomScheduleDTO> getAllSchedules();
    ClassroomScheduleDTO updateSchedule(Long id, ClassroomScheduleDTO scheduleDTO);
    void deleteSchedule(Long id);

    // Query Operations
    List<ClassroomScheduleDTO> getSchedulesByDayOfWeek(DayOfWeek dayOfWeek);
    List<ClassroomScheduleDTO> getSchedulesByDayOfWeekAndTimeRange(DayOfWeek dayOfWeek, Time startTime, Time endTime);
    List<ClassroomScheduleDTO> getSchedulesStartingBefore(DayOfWeek dayOfWeek, Time time);
    List<ClassroomScheduleDTO> getSchedulesEndingAfter(DayOfWeek dayOfWeek, Time time);
    List<ClassroomScheduleDTO> getSchedulesWithMultipleDays();
    List<ClassroomScheduleDTO> getSchedulesWithSingleDay();
    List<ClassroomScheduleDTO> getSchedulesByDayCount(int dayCount);
    List<ClassroomScheduleDTO> getWeekdaySchedules();
    List<ClassroomScheduleDTO> getWeekendSchedules();
    List<ClassroomScheduleDTO> getConflictingSchedules(DayOfWeek dayOfWeek, Time startTime, Time endTime);
    List<ClassroomScheduleDTO> getSchedulesByMinDuration(int minDurationMinutes);

    // Day Management
    ClassroomScheduleDTO addDayToSchedule(Long scheduleId, DayDTO dayDTO);
    ClassroomScheduleDTO removeDayFromSchedule(Long scheduleId, DayOfWeek dayOfWeek, Time startTime);
    ClassroomScheduleDTO addMultipleDaysToSchedule(Long scheduleId, List<DayDTO> days);
    ClassroomScheduleDTO updateDayInSchedule(Long scheduleId, DayOfWeek oldDayOfWeek, Time oldStartTime, DayDTO newDayDTO);
}
