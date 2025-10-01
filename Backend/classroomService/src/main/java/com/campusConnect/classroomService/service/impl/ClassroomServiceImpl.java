package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.ClassroomDTO;
import com.campusConnect.classroomService.entity.Classroom;
import com.campusConnect.classroomService.entity.ClassroomSchedule;
import com.campusConnect.classroomService.repository.ClassroomRepository;
import com.campusConnect.classroomService.repository.ClassroomScheduleRepository;
import com.campusConnect.classroomService.service.ClassroomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public ClassroomDTO createClassroom(ClassroomDTO classroomDTO) {
        log.info("Creating new classroom for teacher: {} with subject: {}",
                classroomDTO.getTeacherId(), classroomDTO.getSubject());

        Classroom classroom = modelMapper.map(classroomDTO, Classroom.class);

        // Handle schedule assignment if provided
        if (classroomDTO.getScheduleId() != null) {
            ClassroomSchedule schedule = scheduleRepository.findById(classroomDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + classroomDTO.getScheduleId()));
            classroom.setSchedule(schedule);
        }

        Classroom savedClassroom = classroomRepository.save(classroom);
        log.info("Classroom created successfully with ID: {}", savedClassroom.getId());

        return mapToDTO(savedClassroom);
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomDTO getClassroomById(Long id) {
        log.info("Fetching classroom with ID: {}", id);

        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + id));

        return mapToDTO(classroom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getAllClassrooms() {
        log.info("Fetching all classrooms");

        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO) {
        log.info("Updating classroom with ID: {}", id);

        Classroom existingClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + id));

        // Update fields
        existingClassroom.setTeacherId(classroomDTO.getTeacherId());
        existingClassroom.setSubject(classroomDTO.getSubject());
        existingClassroom.setSemester(classroomDTO.getSemester());
        existingClassroom.setDescription(classroomDTO.getDescription());

        // Handle schedule update
        if (classroomDTO.getScheduleId() != null) {
            ClassroomSchedule schedule = scheduleRepository.findById(classroomDTO.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + classroomDTO.getScheduleId()));
            existingClassroom.setSchedule(schedule);
        } else {
            existingClassroom.setSchedule(null);
        }

        Classroom updatedClassroom = classroomRepository.save(existingClassroom);
        log.info("Classroom updated successfully with ID: {}", updatedClassroom.getId());

        return mapToDTO(updatedClassroom);
    }

    @Override
    public void deleteClassroom(Long id) {
        log.info("Deleting classroom with ID: {}", id);

        if (!classroomRepository.existsById(id)) {
            throw new RuntimeException("Classroom not found with ID: " + id);
        }

        classroomRepository.deleteById(id);
        log.info("Classroom deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsByTeacher(Long teacherId) {
        log.info("Fetching classrooms for teacher: {}", teacherId);

        List<Classroom> classrooms = classroomRepository.findByTeacherId(teacherId);
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsBySemester(Integer semester) {
        log.info("Fetching classrooms for semester: {}", semester);

        List<Classroom> classrooms = classroomRepository.findBySemester(semester);
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> searchClassroomsBySubject(String subject) {
        log.info("Searching classrooms by subject: {}", subject);

        List<Classroom> classrooms = classroomRepository.findBySubjectContainingIgnoreCase(subject);
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsByTeacherAndSemester(Long teacherId, Integer semester) {
        log.info("Fetching classrooms for teacher: {} and semester: {}", teacherId, semester);

        List<Classroom> classrooms = classroomRepository.findByTeacherIdAndSemester(teacherId, semester);
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsWithSchedule() {
        log.info("Fetching classrooms with schedule");

        List<Classroom> classrooms = classroomRepository.findClassroomsWithSchedule();
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsWithoutSchedule() {
        log.info("Fetching classrooms without schedule");

        List<Classroom> classrooms = classroomRepository.findClassroomsWithoutSchedule();
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsBySubjectAndSemester(String subject, Integer semester) {
        log.info("Fetching classrooms for subject: {} and semester: {}", subject, semester);

        List<Classroom> classrooms = classroomRepository.findBySubjectAndSemester(subject, semester);
        return classrooms.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO assignSchedule(Long classroomId, Long scheduleId) {
        log.info("Assigning schedule {} to classroom {}", scheduleId, classroomId);

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + classroomId));

        ClassroomSchedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + scheduleId));

        classroom.setSchedule(schedule);
        Classroom updatedClassroom = classroomRepository.save(classroom);

        return mapToDTO(updatedClassroom);
    }

    @Override
    public ClassroomDTO removeSchedule(Long classroomId) {
        log.info("Removing schedule from classroom {}", classroomId);

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + classroomId));

        classroom.setSchedule(null);
        Classroom updatedClassroom = classroomRepository.save(classroom);

        return mapToDTO(updatedClassroom);
    }

    private ClassroomDTO mapToDTO(Classroom classroom) {
        ClassroomDTO dto = modelMapper.map(classroom, ClassroomDTO.class);
        if (classroom.getSchedule() != null) {
            dto.setScheduleId(classroom.getSchedule().getId());
        }
        return dto;
    }
}
