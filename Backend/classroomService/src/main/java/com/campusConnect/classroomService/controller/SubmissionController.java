package com.campusConnect.classroomService.controller;

import com.campusConnect.classroomService.dto.SubmissionDTO;
import com.campusConnect.classroomService.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SubmissionController {

    private final SubmissionService submissionService;

    // Create submission
    @PostMapping
    public ResponseEntity<SubmissionDTO> createSubmission(@Valid @RequestBody SubmissionDTO submissionDTO) {
        log.info("Request to create submission for assignment: {} by student: {}",
                submissionDTO.getAssignmentId(), submissionDTO.getStudentId());

        try {
            SubmissionDTO createdSubmission = submissionService.createSubmission(submissionDTO);
            return new ResponseEntity<>(createdSubmission, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating submission: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all submissions
    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        log.info("Request to get all submissions");

        try {
            List<SubmissionDTO> submissions = submissionService.getAllSubmissions();
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all submissions: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submission by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        log.info("Request to get submission with ID: {}", id);

        try {
            SubmissionDTO submission = submissionService.getSubmissionById(id);
            return new ResponseEntity<>(submission, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submission with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update submission
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> updateSubmission(@PathVariable Long id,
                                                          @Valid @RequestBody SubmissionDTO submissionDTO) {
        log.info("Request to update submission with ID: {}", id);

        try {
            SubmissionDTO updatedSubmission = submissionService.updateSubmission(id, submissionDTO);
            return new ResponseEntity<>(updatedSubmission, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating submission with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete submission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        log.info("Request to delete submission with ID: {}", id);

        try {
            submissionService.deleteSubmission(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting submission with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get submissions by assignment
    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByAssignment(@PathVariable Long assignmentId) {
        log.info("Request to get submissions for assignment: {}", assignmentId);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByAssignment(assignmentId);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by assignment {}: {}", assignmentId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByStudent(@PathVariable String studentId) {
        log.info("Request to get submissions for student: {}", studentId);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByStudent(studentId);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by student {}: {}", studentId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submission by assignment and student
    @GetMapping("/assignment/{assignmentId}/student/{studentId}")
    public ResponseEntity<SubmissionDTO> getSubmissionByAssignmentAndStudent(
            @PathVariable Long assignmentId,
            @PathVariable String studentId) {
        log.info("Request to get submission for assignment: {} and student: {}", assignmentId, studentId);

        try {
            SubmissionDTO submission = submissionService.getSubmissionByAssignmentAndStudent(assignmentId, studentId);
            return new ResponseEntity<>(submission, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submission by assignment and student: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get submissions by grade
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByGrade(@PathVariable String grade) {
        log.info("Request to get submissions with grade: {}", grade);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByGrade(grade);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by grade {}: {}", grade, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions with grade
    @GetMapping("/graded")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsWithGrade() {
        log.info("Request to get submissions with grade assigned");

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsWithGrade();
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions with grade: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get ungraded submissions
    @GetMapping("/ungraded")
    public ResponseEntity<List<SubmissionDTO>> getUngradedSubmissions() {
        log.info("Request to get ungraded submissions");

        try {
            List<SubmissionDTO> submissions = submissionService.getUngradedSubmissions();
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching ungraded submissions: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by assignment and grade
    @GetMapping("/assignment/{assignmentId}/grade/{grade}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByAssignmentAndGrade(
            @PathVariable Long assignmentId,
            @PathVariable String grade) {
        log.info("Request to get submissions for assignment: {} with grade: {}", assignmentId, grade);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByAssignmentAndGrade(assignmentId, grade);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by assignment and grade: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by student and grade
    @GetMapping("/student/{studentId}/grade/{grade}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByStudentAndGrade(
            @PathVariable String studentId,
            @PathVariable String grade) {
        log.info("Request to get submissions for student: {} with grade: {}", studentId, grade);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByStudentAndGrade(studentId, grade);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by student and grade: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by classroom
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByClassroom(@PathVariable Long classroomId) {
        log.info("Request to get submissions for classroom: {}", classroomId);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByClassroom(classroomId);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by classroom: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by teacher
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByTeacher(@PathVariable Long teacherId) {
        log.info("Request to get submissions for teacher: {}", teacherId);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByTeacher(teacherId);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by teacher: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by subject
    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsBySubject(@PathVariable String subject) {
        log.info("Request to get submissions for subject: {}", subject);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsBySubject(subject);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by subject: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by semester
    @GetMapping("/semester/{semester}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsBySemester(@PathVariable Integer semester) {
        log.info("Request to get submissions for semester: {}", semester);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsBySemester(semester);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by semester: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get submissions by grade range
    @GetMapping("/grade-range")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByGradeRange(
            @RequestParam Double minGrade,
            @RequestParam Double maxGrade) {
        log.info("Request to get submissions with grade between {} and {}", minGrade, maxGrade);

        try {
            List<SubmissionDTO> submissions = submissionService.getSubmissionsByGradeRange(minGrade, maxGrade);
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching submissions by grade range: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get distinct grades
    @GetMapping("/distinct/grades")
    public ResponseEntity<List<String>> getDistinctGrades() {
        log.info("Request to get distinct grades");

        try {
            List<String> grades = submissionService.getDistinctGrades();
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching distinct grades: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get students who haven't submitted
    @GetMapping("/assignment/{assignmentId}/missing-submissions")
    public ResponseEntity<List<String>> getStudentsWhoHaventSubmitted(@PathVariable Long assignmentId) {
        log.info("Request to get students who haven't submitted for assignment: {}", assignmentId);

        try {
            List<String> studentIds = submissionService.getStudentsWhoHaventSubmitted(assignmentId);
            return new ResponseEntity<>(studentIds, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching students who haven't submitted: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count submissions by assignment
    @GetMapping("/count/assignment/{assignmentId}")
    public ResponseEntity<Long> countSubmissionsByAssignment(@PathVariable Long assignmentId) {
        log.info("Request to count submissions for assignment: {}", assignmentId);

        try {
            Long count = submissionService.countSubmissionsByAssignment(assignmentId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting submissions by assignment: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count submissions by student
    @GetMapping("/count/student/{studentId}")
    public ResponseEntity<Long> countSubmissionsByStudent(@PathVariable String studentId) {
        log.info("Request to count submissions for student: {}", studentId);

        try {
            Long count = submissionService.countSubmissionsByStudent(studentId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting submissions by student: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count graded submissions by assignment
    @GetMapping("/count/assignment/{assignmentId}/graded")
    public ResponseEntity<Long> countGradedSubmissionsByAssignment(@PathVariable Long assignmentId) {
        log.info("Request to count graded submissions for assignment: {}", assignmentId);

        try {
            Long count = submissionService.countGradedSubmissionsByAssignment(assignmentId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting graded submissions by assignment: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count ungraded submissions by assignment
    @GetMapping("/count/assignment/{assignmentId}/ungraded")
    public ResponseEntity<Long> countUngradedSubmissionsByAssignment(@PathVariable Long assignmentId) {
        log.info("Request to count ungraded submissions for assignment: {}", assignmentId);

        try {
            Long count = submissionService.countUngradedSubmissionsByAssignment(assignmentId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting ungraded submissions by assignment: {}", e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate average grade by assignment
    @GetMapping("/assignment/{assignmentId}/average-grade")
    public ResponseEntity<Double> calculateAverageGradeByAssignment(@PathVariable Long assignmentId) {
        log.info("Request to calculate average grade for assignment: {}", assignmentId);

        try {
            Double average = submissionService.calculateAverageGradeByAssignment(assignmentId);
            return new ResponseEntity<>(average, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating average grade by assignment: {}", e.getMessage());
            return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate average grade by student
    @GetMapping("/student/{studentId}/average-grade")
    public ResponseEntity<Double> calculateAverageGradeByStudent(@PathVariable String studentId) {
        log.info("Request to calculate average grade for student: {}", studentId);

        try {
            Double average = submissionService.calculateAverageGradeByStudent(studentId);
            return new ResponseEntity<>(average, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error calculating average grade by student: {}", e.getMessage());
            return new ResponseEntity<>(0.0, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Grade submission
    @PatchMapping("/{submissionId}/grade")
    public ResponseEntity<SubmissionDTO> gradeSubmission(@PathVariable Long submissionId,
                                                         @RequestParam String grade) {
        log.info("Request to grade submission {} with grade: {}", submissionId, grade);

        try {
            SubmissionDTO gradedSubmission = submissionService.gradeSubmission(submissionId, grade);
            return new ResponseEntity<>(gradedSubmission, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error grading submission {}: {}", submissionId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Grade multiple submissions
    @PatchMapping("/grade-multiple")
    public ResponseEntity<List<SubmissionDTO>> gradeMultipleSubmissions(
            @RequestParam List<Long> submissionIds,
            @RequestParam List<String> grades) {
        log.info("Request to grade {} submissions", submissionIds.size());

        try {
            List<SubmissionDTO> gradedSubmissions = submissionService.gradeMultipleSubmissions(submissionIds, grades);
            return new ResponseEntity<>(gradedSubmissions, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error grading multiple submissions: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Check if student has submitted
    @GetMapping("/assignment/{assignmentId}/student/{studentId}/exists")
    public ResponseEntity<Boolean> hasStudentSubmitted(@PathVariable Long assignmentId,
                                                       @PathVariable String studentId) {
        log.info("Request to check if student {} has submitted for assignment {}", studentId, assignmentId);

        try {
            boolean hasSubmitted = submissionService.hasStudentSubmitted(assignmentId, studentId);
            return new ResponseEntity<>(hasSubmitted, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking student submission: {}", e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get or create submission
    @PostMapping("/assignment/{assignmentId}/student/{studentId}/submit")
    public ResponseEntity<SubmissionDTO> getOrCreateSubmission(
            @PathVariable Long assignmentId,
            @PathVariable String studentId,
            @RequestParam String fileUrl) {
        log.info("Request to get or create submission for assignment {} and student {}", assignmentId, studentId);

        try {
            SubmissionDTO submission = submissionService.getOrCreateSubmission(assignmentId, studentId, fileUrl);
            return new ResponseEntity<>(submission, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting or creating submission: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
