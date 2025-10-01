package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Exam;
import com.campusConnect.classroomService.entity.enums.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // Find exams by classroom ID
    List<Exam> findByClassroomId(Long classroomId);

    // Find exams by type
    List<Exam> findByType(ExamType type);

    // Find exams by date
    List<Exam> findByDate(Date date);

    // Find exams by classroom and type
    List<Exam> findByClassroomIdAndType(Long classroomId, ExamType type);

    // Find exams by classroom and date
    List<Exam> findByClassroomIdAndDate(Long classroomId, Date date);

    // Find exams by type and date
    List<Exam> findByTypeAndDate(ExamType type, Date date);

    // Find exams by date range
    List<Exam> findByDateBetween(Date startDate, Date endDate);

    // Find exams by classroom and date range
    List<Exam> findByClassroomIdAndDateBetween(Long classroomId, Date startDate, Date endDate);

    // Find exams by marks range
    List<Exam> findByMarksBetween(Float minMarks, Float maxMarks);

    // Find exams by classroom and marks range
    List<Exam> findByClassroomIdAndMarksBetween(Long classroomId, Float minMarks, Float maxMarks);

    // Find exams by date before
    List<Exam> findByDateBefore(Date date);

    // Find exams by date after
    List<Exam> findByDateAfter(Date date);

    // Find exams by teacher (through classroom)
    @Query("SELECT e FROM Exam e WHERE e.classroom.teacherId = :teacherId")
    List<Exam> findExamsByTeacher(@Param("teacherId") Long teacherId);

    // Find exams by subject (through classroom)
    @Query("SELECT e FROM Exam e WHERE LOWER(e.classroom.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Exam> findExamsBySubject(@Param("subject") String subject);

    // Find exams by semester (through classroom)
    @Query("SELECT e FROM Exam e WHERE e.classroom.semester = :semester")
    List<Exam> findExamsBySemester(@Param("semester") Integer semester);

    // Find exams with marks above threshold
    @Query("SELECT e FROM Exam e WHERE e.marks >= :threshold")
    List<Exam> findExamsWithMarksAbove(@Param("threshold") Float threshold);

    // Find exams with marks below threshold
    @Query("SELECT e FROM Exam e WHERE e.marks < :threshold")
    List<Exam> findExamsWithMarksBelow(@Param("threshold") Float threshold);

    // Calculate average marks for classroom
    @Query("SELECT AVG(e.marks) FROM Exam e WHERE e.classroom.id = :classroomId")
    Double calculateAverageMarksByClassroom(@Param("classroomId") Long classroomId);

    // Calculate average marks by exam type
    @Query("SELECT AVG(e.marks) FROM Exam e WHERE e.type = :type")
    Double calculateAverageMarksByType(@Param("type") ExamType type);

    // Count exams by classroom
    @Query("SELECT COUNT(e) FROM Exam e WHERE e.classroom.id = :classroomId")
    Long countExamsByClassroom(@Param("classroomId") Long classroomId);

    // Count exams by type
    @Query("SELECT COUNT(e) FROM Exam e WHERE e.type = :type")
    Long countExamsByType(@Param("type") ExamType type);

    // Find upcoming exams
    @Query("SELECT e FROM Exam e WHERE e.date > :currentDate ORDER BY e.date ASC")
    List<Exam> findUpcomingExams(@Param("currentDate") Date currentDate);

    // Find past exams
    @Query("SELECT e FROM Exam e WHERE e.date < :currentDate ORDER BY e.date DESC")
    List<Exam> findPastExams(@Param("currentDate") Date currentDate);
}
