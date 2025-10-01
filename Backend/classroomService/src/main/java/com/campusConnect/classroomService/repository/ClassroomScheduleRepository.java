package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.ClassroomSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ClassroomScheduleRepository extends JpaRepository<ClassroomSchedule, Long> {

    // Find schedules that contain a specific day of week
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day = :dayOfWeek")
    List<ClassroomSchedule> findByDayOfWeek(@Param("dayOfWeek") DayOfWeek dayOfWeek);

    // Find schedules by day of week and time range
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day = :dayOfWeek AND d.startTime <= :endTime AND d.endTime >= :startTime")
    List<ClassroomSchedule> findByDayOfWeekAndTimeRange(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                                        @Param("startTime") Time startTime,
                                                        @Param("endTime") Time endTime);

    // Find schedules that start before a specific time on a specific day
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day = :dayOfWeek AND d.startTime <= :time")
    List<ClassroomSchedule> findSchedulesStartingBefore(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                                        @Param("time") Time time);

    // Find schedules that end after a specific time on a specific day
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day = :dayOfWeek AND d.endTime >= :time")
    List<ClassroomSchedule> findSchedulesEndingAfter(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                                     @Param("time") Time time);

    // Find schedules with multiple days (more than one day)
    @Query("SELECT cs FROM ClassroomSchedule cs WHERE SIZE(cs.day) > 1")
    List<ClassroomSchedule> findSchedulesWithMultipleDays();

    // Find schedules with single day only
    @Query("SELECT cs FROM ClassroomSchedule cs WHERE SIZE(cs.day) = 1")
    List<ClassroomSchedule> findSchedulesWithSingleDay();

    // Find schedules with specific number of days
    @Query("SELECT cs FROM ClassroomSchedule cs WHERE SIZE(cs.day) = :dayCount")
    List<ClassroomSchedule> findSchedulesByDayCount(@Param("dayCount") int dayCount);

    // Find schedules that contain weekdays (Monday to Friday)
    @Query("SELECT DISTINCT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day IN (:weekdays)")
    List<ClassroomSchedule> findWeekdaySchedules(@Param("weekdays") List<DayOfWeek> weekdays);

    // Find schedules that contain weekend days
    @Query("SELECT DISTINCT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE d.day IN (:weekends)")
    List<ClassroomSchedule> findWeekendSchedules(@Param("weekends") List<DayOfWeek> weekends);

    // Find conflicting schedules (overlapping time ranges on same day)
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE " +
            "d.day = :dayOfWeek AND " +
            "((d.startTime <= :endTime AND d.endTime >= :startTime))")
    List<ClassroomSchedule> findConflictingSchedules(@Param("dayOfWeek") DayOfWeek dayOfWeek,
                                                     @Param("startTime") Time startTime,
                                                     @Param("endTime") Time endTime);

    // Find schedules by time duration (difference between start and end time)
    @Query("SELECT cs FROM ClassroomSchedule cs JOIN cs.day d WHERE " +
            "(HOUR(d.endTime) * 60 + MINUTE(d.endTime)) - (HOUR(d.startTime) * 60 + MINUTE(d.startTime)) >= :minDurationMinutes")
    List<ClassroomSchedule> findSchedulesByMinDuration(@Param("minDurationMinutes") int minDurationMinutes);
}
