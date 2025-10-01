package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // Find classrooms by teacher ID
    List<Classroom> findByTeacherId(Long teacherId);

    // Find classrooms by semester
    List<Classroom> findBySemester(Integer semester);

    // Find classrooms by subject (case insensitive)
    List<Classroom> findBySubjectContainingIgnoreCase(String subject);

    // Find classrooms by teacher and semester
    List<Classroom> findByTeacherIdAndSemester(Long teacherId, Integer semester);

    // Find classrooms with schedule
    @Query("SELECT c FROM Classroom c WHERE c.schedule IS NOT NULL")
    List<Classroom> findClassroomsWithSchedule();

    // Find classrooms without schedule
    @Query("SELECT c FROM Classroom c WHERE c.schedule IS NULL")
    List<Classroom> findClassroomsWithoutSchedule();

    // Custom query to find by subject and semester
    @Query("SELECT c FROM Classroom c WHERE LOWER(c.subject) LIKE LOWER(CONCAT('%', :subject, '%')) AND c.semester = :semester")
    List<Classroom> findBySubjectAndSemester(@Param("subject") String subject, @Param("semester") Integer semester);
}
