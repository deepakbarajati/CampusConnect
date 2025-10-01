package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Find assignments by classroom ID
    List<Assignment> findByClassroomId(Long classroomId);

    // Find assignments by title containing (case insensitive)
    List<Assignment> findByTitleContainingIgnoreCase(String title);

    // Find assignments by deadline before specific date
    List<Assignment> findByDeadlineBefore(LocalDateTime deadline);

    // Find assignments by deadline after specific date
    List<Assignment> findByDeadlineAfter(LocalDateTime deadline);

    // Find assignments by deadline between dates
    List<Assignment> findByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find assignments by classroom and deadline before
    List<Assignment> findByClassroomIdAndDeadlineBefore(Long classroomId, LocalDateTime deadline);

    // Find assignments by classroom and deadline after
    List<Assignment> findByClassroomIdAndDeadlineAfter(Long classroomId, LocalDateTime deadline);

    // Find assignments due today
    @Query("SELECT a FROM Assignment a WHERE DATE(a.deadline) = DATE(:today)")
    List<Assignment> findAssignmentsDueToday(@Param("today") LocalDateTime today);

    // Find assignments due this week
    @Query("SELECT a FROM Assignment a WHERE a.deadline >= :startOfWeek AND a.deadline <= :endOfWeek")
    List<Assignment> findAssignmentsDueThisWeek(@Param("startOfWeek") LocalDateTime startOfWeek,
                                                @Param("endOfWeek") LocalDateTime endOfWeek);

    // Find overdue assignments
    @Query("SELECT a FROM Assignment a WHERE a.deadline < :currentTime")
    List<Assignment> findOverdueAssignments(@Param("currentTime") LocalDateTime currentTime);

    // Find assignments due within specified hours
    @Query("SELECT a FROM Assignment a WHERE a.deadline >= :currentTime AND a.deadline <= :endTime")
    List<Assignment> findAssignmentsDueWithinHours(@Param("currentTime") LocalDateTime currentTime,
                                                   @Param("endTime") LocalDateTime endTime);

    // Count assignments by classroom
    @Query("SELECT COUNT(a) FROM Assignment a WHERE a.classroom.id = :classroomId")
    Long countAssignmentsByClassroom(@Param("classroomId") Long classroomId);

    // Find assignments by classroom teacher ID
    @Query("SELECT a FROM Assignment a WHERE a.classroom.teacherId = :teacherId")
    List<Assignment> findAssignmentsByTeacher(@Param("teacherId") Long teacherId);

    // Find assignments by classroom subject
    @Query("SELECT a FROM Assignment a WHERE LOWER(a.classroom.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Assignment> findAssignmentsBySubject(@Param("subject") String subject);

    // Find assignments by classroom semester
    @Query("SELECT a FROM Assignment a WHERE a.classroom.semester = :semester")
    List<Assignment> findAssignmentsBySemester(@Param("semester") Integer semester);
}
