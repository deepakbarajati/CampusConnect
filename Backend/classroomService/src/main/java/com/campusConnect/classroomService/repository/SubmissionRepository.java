package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    // Find submission by assignment ID
    List<Submission> findByAssignmentId(Long assignmentId);

    // Find submission by student ID
    List<Submission> findByStudentId(String studentId);

    // Find submission by assignment and student
    Optional<Submission> findByAssignmentIdAndStudentId(Long assignmentId, String studentId);

    // Find submissions by grade
    List<Submission> findByGrade(String grade);

    // Find submissions with grade not null
    @Query("SELECT s FROM Submission s WHERE s.grade IS NOT NULL")
    List<Submission> findSubmissionsWithGrade();

    // Find submissions without grade (ungraded)
    @Query("SELECT s FROM Submission s WHERE s.grade IS NULL")
    List<Submission> findUngradedSubmissions();

    // Find submissions by assignment and grade
    List<Submission> findByAssignmentIdAndGrade(Long assignmentId, String grade);

    // Find submissions by student and grade
    List<Submission> findByStudentIdAndGrade(String studentId, String grade);

    // Count submissions by assignment
    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assignment.id = :assignmentId")
    Long countSubmissionsByAssignment(@Param("assignmentId") Long assignmentId);

    // Count submissions by student
    @Query("SELECT COUNT(s) FROM Submission s WHERE s.studentId = :studentId")
    Long countSubmissionsByStudent(@Param("studentId") String studentId);

    // Count graded submissions by assignment
    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assignment.id = :assignmentId AND s.grade IS NOT NULL")
    Long countGradedSubmissionsByAssignment(@Param("assignmentId") Long assignmentId);

    // Count ungraded submissions by assignment
    @Query("SELECT COUNT(s) FROM Submission s WHERE s.assignment.id = :assignmentId AND s.grade IS NULL")
    Long countUngradedSubmissionsByAssignment(@Param("assignmentId") Long assignmentId);

    // Find submissions by classroom (through assignment)
    @Query("SELECT s FROM Submission s WHERE s.assignment.classroom.id = :classroomId")
    List<Submission> findSubmissionsByClassroom(@Param("classroomId") Long classroomId);

    // Find submissions by teacher (through assignment and classroom)
    @Query("SELECT s FROM Submission s WHERE s.assignment.classroom.teacherId = :teacherId")
    List<Submission> findSubmissionsByTeacher(@Param("teacherId") Long teacherId);

    // Find submissions by subject (through assignment and classroom)
    @Query("SELECT s FROM Submission s WHERE LOWER(s.assignment.classroom.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Submission> findSubmissionsBySubject(@Param("subject") String subject);

    // Find submissions by semester (through assignment and classroom)
    @Query("SELECT s FROM Submission s WHERE s.assignment.classroom.semester = :semester")
    List<Submission> findSubmissionsBySemester(@Param("semester") Integer semester);

    // Find submissions by grade range (assuming grades are numeric strings)
    @Query("SELECT s FROM Submission s WHERE s.grade IS NOT NULL AND " +
            "CAST(s.grade AS DOUBLE) >= :minGrade AND CAST(s.grade AS DOUBLE) <= :maxGrade")
    List<Submission> findSubmissionsByGradeRange(@Param("minGrade") Double minGrade,
                                                 @Param("maxGrade") Double maxGrade);

    // Calculate average grade by assignment
    @Query("SELECT AVG(CAST(s.grade AS DOUBLE)) FROM Submission s WHERE s.assignment.id = :assignmentId AND s.grade IS NOT NULL")
    Double calculateAverageGradeByAssignment(@Param("assignmentId") Long assignmentId);

    // Calculate average grade by student
    @Query("SELECT AVG(CAST(s.grade AS DOUBLE)) FROM Submission s WHERE s.studentId = :studentId AND s.grade IS NOT NULL")
    Double calculateAverageGradeByStudent(@Param("studentId") String studentId);

    // Find distinct grades
    @Query("SELECT DISTINCT s.grade FROM Submission s WHERE s.grade IS NOT NULL ORDER BY s.grade")
    List<String> findDistinctGrades();

    // Find students who haven't submitted for a specific assignment
    @Query("SELECT DISTINCT s1.studentId FROM Submission s1 WHERE s1.studentId NOT IN " +
            "(SELECT s2.studentId FROM Submission s2 WHERE s2.assignment.id = :assignmentId)")
    List<String> findStudentsWhoHaventSubmitted(@Param("assignmentId") Long assignmentId);
}
