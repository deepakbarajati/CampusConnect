package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.AssignmentDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface AssignmentService {

    // CRUD Operations
    AssignmentDTO createAssignment(AssignmentDTO assignmentDTO);
    AssignmentDTO getAssignmentById(Long id);
    List<AssignmentDTO> getAllAssignments();
    AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO);
    void deleteAssignment(Long id);

    // Query Operations
    List<AssignmentDTO> getAssignmentsByClassroom(Long classroomId);
    List<AssignmentDTO> searchAssignmentsByTitle(String title);
    List<AssignmentDTO> getAssignmentsByDeadlineBefore(LocalDateTime deadline);
    List<AssignmentDTO> getAssignmentsByDeadlineAfter(LocalDateTime deadline);
    List<AssignmentDTO> getAssignmentsByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<AssignmentDTO> getAssignmentsByClassroomAndDeadlineBefore(Long classroomId, LocalDateTime deadline);
    List<AssignmentDTO> getAssignmentsByClassroomAndDeadlineAfter(Long classroomId, LocalDateTime deadline);
    List<AssignmentDTO> getAssignmentsDueToday();
    List<AssignmentDTO> getAssignmentsDueThisWeek();
    List<AssignmentDTO> getOverdueAssignments();
    List<AssignmentDTO> getAssignmentsDueWithinHours(int hours);
    List<AssignmentDTO> getAssignmentsByTeacher(Long teacherId);
    List<AssignmentDTO> getAssignmentsBySubject(String subject);
    List<AssignmentDTO> getAssignmentsBySemester(Integer semester);

    // Utility Methods
    Long countAssignmentsByClassroom(Long classroomId);
    AssignmentDTO extendDeadline(Long assignmentId, LocalDateTime newDeadline);
    List<AssignmentDTO> getUpcomingAssignments(int days);
}
