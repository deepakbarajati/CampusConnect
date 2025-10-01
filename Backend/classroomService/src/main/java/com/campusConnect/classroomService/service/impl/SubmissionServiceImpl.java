package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.SubmissionDTO;
import com.campusConnect.classroomService.entity.Assignment;
import com.campusConnect.classroomService.entity.Submission;
import com.campusConnect.classroomService.repository.AssignmentRepository;
import com.campusConnect.classroomService.repository.SubmissionRepository;
import com.campusConnect.classroomService.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public SubmissionDTO createSubmission(SubmissionDTO submissionDTO) {
        log.info("Creating new submission for assignment: {} by student: {}",
                submissionDTO.getAssignmentId(), submissionDTO.getStudentId());

        // Check if submission already exists
        Optional<Submission> existingSubmission = submissionRepository
                .findByAssignmentIdAndStudentId(submissionDTO.getAssignmentId(), submissionDTO.getStudentId());

        if (existingSubmission.isPresent()) {
            throw new RuntimeException("Submission already exists for this student and assignment");
        }

        // Verify assignment exists
        Assignment assignment = assignmentRepository.findById(submissionDTO.getAssignmentId())
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + submissionDTO.getAssignmentId()));

        Submission submission = modelMapper.map(submissionDTO, Submission.class);
        submission.setAssignment(assignment);

        Submission savedSubmission = submissionRepository.save(submission);
        log.info("Submission created successfully with ID: {}", savedSubmission.getId());

        return mapToDTO(savedSubmission);
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionDTO getSubmissionById(Long id) {
        log.info("Fetching submission with ID: {}", id);

        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with ID: " + id));

        return mapToDTO(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getAllSubmissions() {
        log.info("Fetching all submissions");

        List<Submission> submissions = submissionRepository.findAll();
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubmissionDTO updateSubmission(Long id, SubmissionDTO submissionDTO) {
        log.info("Updating submission with ID: {}", id);

        Submission existingSubmission = submissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with ID: " + id));

        // Update assignment if changed
        if (!existingSubmission.getAssignment().getId().equals(submissionDTO.getAssignmentId())) {
            Assignment assignment = assignmentRepository.findById(submissionDTO.getAssignmentId())
                    .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + submissionDTO.getAssignmentId()));
            existingSubmission.setAssignment(assignment);
        }

        // Update other fields
        existingSubmission.setStudentId(submissionDTO.getStudentId());
        existingSubmission.setFile_url(submissionDTO.getFile_url());
        existingSubmission.setGrade(submissionDTO.getGrade());

        Submission updatedSubmission = submissionRepository.save(existingSubmission);
        log.info("Submission updated successfully with ID: {}", updatedSubmission.getId());

        return mapToDTO(updatedSubmission);
    }

    @Override
    public void deleteSubmission(Long id) {
        log.info("Deleting submission with ID: {}", id);

        if (!submissionRepository.existsById(id)) {
            throw new RuntimeException("Submission not found with ID: " + id);
        }

        submissionRepository.deleteById(id);
        log.info("Submission deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByAssignment(Long assignmentId) {
        log.info("Fetching submissions for assignment: {}", assignmentId);

        List<Submission> submissions = submissionRepository.findByAssignmentId(assignmentId);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByStudent(String studentId) {
        log.info("Fetching submissions for student: {}", studentId);

        List<Submission> submissions = submissionRepository.findByStudentId(studentId);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionDTO getSubmissionByAssignmentAndStudent(Long assignmentId, String studentId) {
        log.info("Fetching submission for assignment: {} and student: {}", assignmentId, studentId);

        Optional<Submission> submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
        return submission.map(this::mapToDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByGrade(String grade) {
        log.info("Fetching submissions with grade: {}", grade);

        List<Submission> submissions = submissionRepository.findByGrade(grade);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsWithGrade() {
        log.info("Fetching submissions with grade assigned");

        List<Submission> submissions = submissionRepository.findSubmissionsWithGrade();
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getUngradedSubmissions() {
        log.info("Fetching ungraded submissions");

        List<Submission> submissions = submissionRepository.findUngradedSubmissions();
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByAssignmentAndGrade(Long assignmentId, String grade) {
        log.info("Fetching submissions for assignment: {} with grade: {}", assignmentId, grade);

        List<Submission> submissions = submissionRepository.findByAssignmentIdAndGrade(assignmentId, grade);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByStudentAndGrade(String studentId, String grade) {
        log.info("Fetching submissions for student: {} with grade: {}", studentId, grade);

        List<Submission> submissions = submissionRepository.findByStudentIdAndGrade(studentId, grade);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByClassroom(Long classroomId) {
        log.info("Fetching submissions for classroom: {}", classroomId);

        List<Submission> submissions = submissionRepository.findSubmissionsByClassroom(classroomId);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByTeacher(Long teacherId) {
        log.info("Fetching submissions for teacher: {}", teacherId);

        List<Submission> submissions = submissionRepository.findSubmissionsByTeacher(teacherId);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsBySubject(String subject) {
        log.info("Fetching submissions for subject: {}", subject);

        List<Submission> submissions = submissionRepository.findSubmissionsBySubject(subject);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsBySemester(Integer semester) {
        log.info("Fetching submissions for semester: {}", semester);

        List<Submission> submissions = submissionRepository.findSubmissionsBySemester(semester);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByGradeRange(Double minGrade, Double maxGrade) {
        log.info("Fetching submissions with grade between {} and {}", minGrade, maxGrade);

        List<Submission> submissions = submissionRepository.findSubmissionsByGradeRange(minGrade, maxGrade);
        return submissions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getDistinctGrades() {
        return submissionRepository.findDistinctGrades();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getStudentsWhoHaventSubmitted(Long assignmentId) {
        return submissionRepository.findStudentsWhoHaventSubmitted(assignmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.countSubmissionsByAssignment(assignmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countSubmissionsByStudent(String studentId) {
        return submissionRepository.countSubmissionsByStudent(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countGradedSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.countGradedSubmissionsByAssignment(assignmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUngradedSubmissionsByAssignment(Long assignmentId) {
        return submissionRepository.countUngradedSubmissionsByAssignment(assignmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageGradeByAssignment(Long assignmentId) {
        Double average = submissionRepository.calculateAverageGradeByAssignment(assignmentId);
        return average != null ? average : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageGradeByStudent(String studentId) {
        Double average = submissionRepository.calculateAverageGradeByStudent(studentId);
        return average != null ? average : 0.0;
    }

    @Override
    public SubmissionDTO gradeSubmission(Long submissionId, String grade) {
        log.info("Grading submission {} with grade: {}", submissionId, grade);

        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found with ID: " + submissionId));

        submission.setGrade(grade);
        Submission updatedSubmission = submissionRepository.save(submission);

        return mapToDTO(updatedSubmission);
    }

    @Override
    public List<SubmissionDTO> gradeMultipleSubmissions(List<Long> submissionIds, List<String> grades) {
        log.info("Grading {} submissions", submissionIds.size());

        if (submissionIds.size() != grades.size()) {
            throw new RuntimeException("Number of submission IDs must match number of grades");
        }

        List<SubmissionDTO> result = new ArrayList<>();
        for (int i = 0; i < submissionIds.size(); i++) {
            try {
                result.add(gradeSubmission(submissionIds.get(i), grades.get(i)));
            } catch (Exception e) {
                log.warn("Failed to grade submission {}: {}", submissionIds.get(i), e.getMessage());
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasStudentSubmitted(Long assignmentId, String studentId) {
        Optional<Submission> submission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);
        return submission.isPresent();
    }

    @Override
    public SubmissionDTO getOrCreateSubmission(Long assignmentId, String studentId, String fileUrl) {
        log.info("Getting or creating submission for assignment: {} and student: {}", assignmentId, studentId);

        Optional<Submission> existingSubmission = submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId);

        if (existingSubmission.isPresent()) {
            Submission submission = existingSubmission.get();
            submission.setFile_url(fileUrl); // Update file URL
            Submission updatedSubmission = submissionRepository.save(submission);
            return mapToDTO(updatedSubmission);
        } else {
            SubmissionDTO newSubmissionDTO = new SubmissionDTO();
            newSubmissionDTO.setAssignmentId(assignmentId);
            newSubmissionDTO.setStudentId(studentId);
            newSubmissionDTO.setFile_url(fileUrl);
            return createSubmission(newSubmissionDTO);
        }
    }

    private SubmissionDTO mapToDTO(Submission submission) {
        SubmissionDTO dto = modelMapper.map(submission, SubmissionDTO.class);
        if (submission.getAssignment() != null) {
            dto.setAssignmentId(submission.getAssignment().getId());
        }
        return dto;
    }
}
