package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.SubmissionDTO;

import java.util.List;

public interface SubmissionService {

    // CRUD Operations
    SubmissionDTO createSubmission(SubmissionDTO submissionDTO);
    SubmissionDTO getSubmissionById(Long id);
    List<SubmissionDTO> getAllSubmissions();
    SubmissionDTO updateSubmission(Long id, SubmissionDTO submissionDTO);
    void deleteSubmission(Long id);

    // Query Operations
    List<SubmissionDTO> getSubmissionsByAssignment(Long assignmentId);
    List<SubmissionDTO> getSubmissionsByStudent(String studentId);
    SubmissionDTO getSubmissionByAssignmentAndStudent(Long assignmentId, String studentId);
    List<SubmissionDTO> getSubmissionsByGrade(String grade);
    List<SubmissionDTO> getSubmissionsWithGrade();
    List<SubmissionDTO> getUngradedSubmissions();
    List<SubmissionDTO> getSubmissionsByAssignmentAndGrade(Long assignmentId, String grade);
    List<SubmissionDTO> getSubmissionsByStudentAndGrade(String studentId, String grade);
    List<SubmissionDTO> getSubmissionsByClassroom(Long classroomId);
    List<SubmissionDTO> getSubmissionsByTeacher(Long teacherId);
    List<SubmissionDTO> getSubmissionsBySubject(String subject);
    List<SubmissionDTO> getSubmissionsBySemester(Integer semester);
    List<SubmissionDTO> getSubmissionsByGradeRange(Double minGrade, Double maxGrade);
    List<String> getDistinctGrades();
    List<String> getStudentsWhoHaventSubmitted(Long assignmentId);

    // Statistics and Analytics
    Long countSubmissionsByAssignment(Long assignmentId);
    Long countSubmissionsByStudent(String studentId);
    Long countGradedSubmissionsByAssignment(Long assignmentId);
    Long countUngradedSubmissionsByAssignment(Long assignmentId);
    Double calculateAverageGradeByAssignment(Long assignmentId);
    Double calculateAverageGradeByStudent(String studentId);

    // Grading Operations
    SubmissionDTO gradeSubmission(Long submissionId, String grade);
    List<SubmissionDTO> gradeMultipleSubmissions(List<Long> submissionIds, List<String> grades);

    // Utility Methods
    boolean hasStudentSubmitted(Long assignmentId, String studentId);
    SubmissionDTO getOrCreateSubmission(Long assignmentId, String studentId, String fileUrl);
}
