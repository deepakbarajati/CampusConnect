package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Attendance;
import com.campusConnect.classroomService.entity.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Find attendance by student and date
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);

    // Find attendance by student and classroom and date
    Optional<Attendance> findByStudentIdAndClassroomIdAndDate(Long studentId, Long classroomId, LocalDate date);

    // Find attendance by student ID
    List<Attendance> findByStudentId(Long studentId);

    // Find attendance by classroom ID
    List<Attendance> findByClassroomId(Long classroomId);

    // Find attendance by date
    List<Attendance> findByDate(LocalDate date);

    // Find attendance by classroom and date
    List<Attendance> findByClassroomIdAndDate(Long classroomId, LocalDate date);

    // Find attendance by student and date range
    List<Attendance> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);

    // Find attendance by classroom and date range
    List<Attendance> findByClassroomIdAndDateBetween(Long classroomId, LocalDate startDate, LocalDate endDate);

    // Find attendance by status
    List<Attendance> findByStatus(AttendanceStatus status);

    // Find attendance by classroom and status
    List<Attendance> findByClassroomIdAndStatus(Long classroomId, AttendanceStatus status);

    // Find attendance by student and status
    List<Attendance> findByStudentIdAndStatus(Long studentId, AttendanceStatus status);

    // Find attendance by marked by
    List<Attendance> findByMarkedBy(Long markedBy);

    // Count attendance by student and status
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId AND a.status = :status")
    Long countByStudentIdAndStatus(@Param("studentId") Long studentId, @Param("status") AttendanceStatus status);

    // Count total attendance days for student
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.studentId = :studentId")
    Long countTotalAttendanceByStudentId(@Param("studentId") Long studentId);

    // Calculate attendance percentage for student (PRESENT + EXCUSED / TOTAL)
    @Query("SELECT (COUNT(CASE WHEN a.status IN ('PRESENT', 'EXCUSED') THEN 1 END) * 100.0 / COUNT(a)) " +
            "FROM Attendance a WHERE a.studentId = :studentId")
    Double calculateAttendancePercentageByStudentId(@Param("studentId") Long studentId);

    // Find students with low attendance in classroom
    @Query("SELECT a.studentId FROM Attendance a WHERE a.classroom.id = :classroomId " +
            "GROUP BY a.studentId " +
            "HAVING (COUNT(CASE WHEN a.status IN ('PRESENT', 'EXCUSED') THEN 1 END) * 100.0 / COUNT(a)) < :percentage")
    List<Long> findStudentsWithLowAttendance(@Param("classroomId") Long classroomId, @Param("percentage") Double percentage);

    // Find attendance by teacher (through classroom)
    @Query("SELECT a FROM Attendance a WHERE a.classroom.teacherId = :teacherId")
    List<Attendance> findAttendanceByTeacher(@Param("teacherId") Long teacherId);

    // Find attendance by classroom and date range with status
    @Query("SELECT a FROM Attendance a WHERE a.classroom.id = :classroomId AND a.date BETWEEN :startDate AND :endDate AND a.status = :status")
    List<Attendance> findByClassroomAndDateRangeAndStatus(@Param("classroomId") Long classroomId,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate,
                                                          @Param("status") AttendanceStatus status);

    // Find absent students by date
    @Query("SELECT a FROM Attendance a WHERE a.status = 'ABSENT' AND a.date = :date")
    List<Attendance> findAbsentStudentsByDate(@Param("date") LocalDate date);

    // Find excused students by date
    @Query("SELECT a FROM Attendance a WHERE a.status = 'EXCUSED' AND a.date = :date")
    List<Attendance> findExcusedStudentsByDate(@Param("date") LocalDate date);

    // Count attendance by classroom and status
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.classroom.id = :classroomId AND a.status = :status")
    Long countByClassroomIdAndStatus(@Param("classroomId") Long classroomId, @Param("status") AttendanceStatus status);
}
