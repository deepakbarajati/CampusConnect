package com.campusConnect.classroomService.repository;

import com.campusConnect.classroomService.entity.Resource;
import com.campusConnect.classroomService.entity.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    // Find resources by uploaded by user
    List<Resource> findByUploadedBy(Long uploadedBy);

    // Find resources by type
    List<Resource> findByType(Type type);

    // Find resources by subject
    List<Resource> findBySubject(String subject);

    // Find resources by semester
    List<Resource> findBySemester(Integer semester);

    // Find resources by subject and semester
    List<Resource> findBySubjectAndSemester(String subject, Integer semester);

    // Find resources by type and subject
    List<Resource> findByTypeAndSubject(Type type, String subject);

    // Find resources by type and semester
    List<Resource> findByTypeAndSemester(Type type, Integer semester);

    // Find resources by uploaded by and type
    List<Resource> findByUploadedByAndType(Long uploadedBy, Type type);

    // Find resources by uploaded by and subject
    List<Resource> findByUploadedByAndSubject(Long uploadedBy, String subject);

    // Find resources by uploaded by and semester
    List<Resource> findByUploadedByAndSemester(Long uploadedBy, Integer semester);

    // Find resources by title containing (case insensitive)
    List<Resource> findByTitleContainingIgnoreCase(String title);

    // Find resources by subject containing (case insensitive)
    List<Resource> findBySubjectContainingIgnoreCase(String subject);

    // Find resources by type, subject and semester
    List<Resource> findByTypeAndSubjectAndSemester(Type type, String subject, Integer semester);

    // Find resources by uploaded by, subject and semester
    List<Resource> findByUploadedByAndSubjectAndSemester(Long uploadedBy, String subject, Integer semester);

    // Count resources by uploaded by user
    @Query("SELECT COUNT(r) FROM Resource r WHERE r.uploadedBy = :uploadedBy")
    Long countResourcesByUploadedBy(@Param("uploadedBy") Long uploadedBy);

    // Count resources by type
    @Query("SELECT COUNT(r) FROM Resource r WHERE r.type = :type")
    Long countResourcesByType(@Param("type") Type type);

    // Count resources by subject
    @Query("SELECT COUNT(r) FROM Resource r WHERE r.subject = :subject")
    Long countResourcesBySubject(@Param("subject") String subject);

    // Count resources by semester
    @Query("SELECT COUNT(r) FROM Resource r WHERE r.semester = :semester")
    Long countResourcesBySemester(@Param("semester") Integer semester);

    // Find distinct subjects
    @Query("SELECT DISTINCT r.subject FROM Resource r ORDER BY r.subject")
    List<String> findDistinctSubjects();

    // Find distinct semesters
    @Query("SELECT DISTINCT r.semester FROM Resource r ORDER BY r.semester")
    List<Integer> findDistinctSemesters();

    // Find resources by multiple types
    @Query("SELECT r FROM Resource r WHERE r.type IN :types")
    List<Resource> findResourcesByTypes(@Param("types") List<Type> types);

    // Find resources by multiple subjects
    @Query("SELECT r FROM Resource r WHERE r.subject IN :subjects")
    List<Resource> findResourcesBySubjects(@Param("subjects") List<String> subjects);

    // Find resources by multiple semesters
    @Query("SELECT r FROM Resource r WHERE r.semester IN :semesters")
    List<Resource> findResourcesBySemesters(@Param("semesters") List<Integer> semesters);

    // Search resources by title and subject
    @Query("SELECT r FROM Resource r WHERE " +
            "LOWER(r.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(r.subject) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Resource> searchResourcesByTitleOrSubject(@Param("searchTerm") String searchTerm);
}
