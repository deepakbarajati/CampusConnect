package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.AssignmentDTO;
import com.campusConnect.classroomService.entity.Assignment;
import com.campusConnect.classroomService.entity.Classroom;
import com.campusConnect.classroomService.repository.AssignmentRepository;
import com.campusConnect.classroomService.repository.ClassroomRepository;
import com.campusConnect.classroomService.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper modelMapper;

    @Override
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        log.info("Creating new assignment: {} for classroom: {}",
                assignmentDTO.getTitle(), assignmentDTO.getClassroomId());

        // Verify classroom exists
        Classroom classroom = classroomRepository.findById(assignmentDTO.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + assignmentDTO.getClassroomId()));

        Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
        assignment.setClassroom(classroom);

        Assignment savedAssignment = assignmentRepository.save(assignment);
        log.info("Assignment created successfully with ID: {}", savedAssignment.getId());

        return mapToDTO(savedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public AssignmentDTO getAssignmentById(Long id) {
        log.info("Fetching assignment with ID: {}", id);

        Assignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));

        return mapToDTO(assignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAllAssignments() {
        log.info("Fetching all assignments");

        List<Assignment> assignments = assignmentRepository.findAll();
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AssignmentDTO updateAssignment(Long id, AssignmentDTO assignmentDTO) {
        log.info("Updating assignment with ID: {}", id);

        Assignment existingAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));

        // Update classroom if changed
        if (!existingAssignment.getClassroom().getId().equals(assignmentDTO.getClassroomId())) {
            Classroom classroom = classroomRepository.findById(assignmentDTO.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + assignmentDTO.getClassroomId()));
            existingAssignment.setClassroom(classroom);
        }

        // Update other fields
        existingAssignment.setTitle(assignmentDTO.getTitle());
        existingAssignment.setDescription(assignmentDTO.getDescription());
        existingAssignment.setDeadline(assignmentDTO.getDeadline());

        Assignment updatedAssignment = assignmentRepository.save(existingAssignment);
        log.info("Assignment updated successfully with ID: {}", updatedAssignment.getId());

        return mapToDTO(updatedAssignment);
    }

    @Override
    public void deleteAssignment(Long id) {
        log.info("Deleting assignment with ID: {}", id);

        if (!assignmentRepository.existsById(id)) {
            throw new RuntimeException("Assignment not found with ID: " + id);
        }

        assignmentRepository.deleteById(id);
        log.info("Assignment deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByClassroom(Long classroomId) {
        log.info("Fetching assignments for classroom: {}", classroomId);

        List<Assignment> assignments = assignmentRepository.findByClassroomId(classroomId);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> searchAssignmentsByTitle(String title) {
        log.info("Searching assignments by title: {}", title);

        List<Assignment> assignments = assignmentRepository.findByTitleContainingIgnoreCase(title);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByDeadlineBefore(LocalDateTime deadline) {
        log.info("Fetching assignments with deadline before: {}", deadline);

        List<Assignment> assignments = assignmentRepository.findByDeadlineBefore(deadline);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByDeadlineAfter(LocalDateTime deadline) {
        log.info("Fetching assignments with deadline after: {}", deadline);

        List<Assignment> assignments = assignmentRepository.findByDeadlineAfter(deadline);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByDeadlineBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching assignments with deadline between: {} and {}", startDate, endDate);

        List<Assignment> assignments = assignmentRepository.findByDeadlineBetween(startDate, endDate);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByClassroomAndDeadlineBefore(Long classroomId, LocalDateTime deadline) {
        log.info("Fetching assignments for classroom: {} with deadline before: {}", classroomId, deadline);

        List<Assignment> assignments = assignmentRepository.findByClassroomIdAndDeadlineBefore(classroomId, deadline);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByClassroomAndDeadlineAfter(Long classroomId, LocalDateTime deadline) {
        log.info("Fetching assignments for classroom: {} with deadline after: {}", classroomId, deadline);

        List<Assignment> assignments = assignmentRepository.findByClassroomIdAndDeadlineAfter(classroomId, deadline);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsDueToday() {
        log.info("Fetching assignments due today");

        List<Assignment> assignments = assignmentRepository.findAssignmentsDueToday(LocalDateTime.now());
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsDueThisWeek() {
        log.info("Fetching assignments due this week");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfWeek = startOfWeek.plusDays(7);

        List<Assignment> assignments = assignmentRepository.findAssignmentsDueThisWeek(startOfWeek, endOfWeek);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getOverdueAssignments() {
        log.info("Fetching overdue assignments");

        List<Assignment> assignments = assignmentRepository.findOverdueAssignments(LocalDateTime.now());
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsDueWithinHours(int hours) {
        log.info("Fetching assignments due within {} hours", hours);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusHours(hours);

        List<Assignment> assignments = assignmentRepository.findAssignmentsDueWithinHours(now, endTime);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsByTeacher(Long teacherId) {
        log.info("Fetching assignments for teacher: {}", teacherId);

        List<Assignment> assignments = assignmentRepository.findAssignmentsByTeacher(teacherId);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsBySubject(String subject) {
        log.info("Fetching assignments for subject: {}", subject);

        List<Assignment> assignments = assignmentRepository.findAssignmentsBySubject(subject);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getAssignmentsBySemester(Integer semester) {
        log.info("Fetching assignments for semester: {}", semester);

        List<Assignment> assignments = assignmentRepository.findAssignmentsBySemester(semester);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAssignmentsByClassroom(Long classroomId) {
        return assignmentRepository.countAssignmentsByClassroom(classroomId);
    }

    @Override
    public AssignmentDTO extendDeadline(Long assignmentId, LocalDateTime newDeadline) {
        log.info("Extending deadline for assignment {} to {}", assignmentId, newDeadline);

        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + assignmentId));

        assignment.setDeadline(newDeadline);
        Assignment updatedAssignment = assignmentRepository.save(assignment);

        return mapToDTO(updatedAssignment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignmentDTO> getUpcomingAssignments(int days) {
        log.info("Fetching assignments due within {} days", days);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusDays(days);

        List<Assignment> assignments = assignmentRepository.findByDeadlineBetween(now, endDate);
        return assignments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AssignmentDTO mapToDTO(Assignment assignment) {
        AssignmentDTO dto = modelMapper.map(assignment, AssignmentDTO.class);
        if (assignment.getClassroom() != null) {
            dto.setClassroomId(assignment.getClassroom().getId());
        }
        return dto;
    }
}
