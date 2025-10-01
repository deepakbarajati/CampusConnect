package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.AttendanceDTO;
import com.campusConnect.classroomService.entity.Attendance;
import com.campusConnect.classroomService.entity.Classroom;
import com.campusConnect.classroomService.entity.enums.AttendanceStatus;
import com.campusConnect.classroomService.repository.AttendanceRepository;
import com.campusConnect.classroomService.repository.ClassroomRepository;
import com.campusConnect.classroomService.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDTO markAttendance(AttendanceDTO attendanceDTO) {
        log.info("Marking attendance for student: {} in classroom: {} on date: {} with status: {}",
                attendanceDTO.getStudentId(), attendanceDTO.getClassroomId(),
                attendanceDTO.getDate(), attendanceDTO.getStatus());

        // Check if attendance already exists
        Optional<Attendance> existingAttendance = attendanceRepository
                .findByStudentIdAndClassroomIdAndDate(attendanceDTO.getStudentId(),
                        attendanceDTO.getClassroomId(),
                        attendanceDTO.getDate());

        if (existingAttendance.isPresent()) {
            throw new RuntimeException("Attendance already marked for this student on this date");
        }

        // Verify classroom exists
        Classroom classroom = classroomRepository.findById(attendanceDTO.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + attendanceDTO.getClassroomId()));

        Attendance attendance = modelMapper.map(attendanceDTO, Attendance.class);
        attendance.setClassroom(classroom);
        attendance.setCreatedAt(LocalDateTime.now());
        attendance.setUpdatedAt(LocalDateTime.now());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        log.info("Attendance marked successfully with ID: {}", savedAttendance.getId());

        return mapToDTO(savedAttendance);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDTO getAttendanceById(Long id) {
        log.info("Fetching attendance with ID: {}", id);

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));

        return mapToDTO(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAllAttendance() {
        log.info("Fetching all attendance records");

        List<Attendance> attendanceList = attendanceRepository.findAll();
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO) {
        log.info("Updating attendance with ID: {}", id);

        Attendance existingAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found with ID: " + id));

        // Update classroom if changed
        if (!existingAttendance.getClassroom().getId().equals(attendanceDTO.getClassroomId())) {
            Classroom classroom = classroomRepository.findById(attendanceDTO.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + attendanceDTO.getClassroomId()));
            existingAttendance.setClassroom(classroom);
        }

        // Update fields
        existingAttendance.setStudentId(attendanceDTO.getStudentId());
        existingAttendance.setDate(attendanceDTO.getDate());
        existingAttendance.setStatus(attendanceDTO.getStatus());
        existingAttendance.setMarkedBy(attendanceDTO.getMarkedBy());
        existingAttendance.setUpdatedAt(LocalDateTime.now());

        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);
        log.info("Attendance updated successfully with ID: {}", updatedAttendance.getId());

        return mapToDTO(updatedAttendance);
    }

    @Override
    public void deleteAttendance(Long id) {
        log.info("Deleting attendance with ID: {}", id);

        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found with ID: " + id);
        }

        attendanceRepository.deleteById(id);
        log.info("Attendance deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDTO getAttendanceByStudentAndDate(Long studentId, LocalDate date) {
        log.info("Fetching attendance for student: {} on date: {}", studentId, date);

        Optional<Attendance> attendance = attendanceRepository.findByStudentIdAndDate(studentId, date);
        return attendance.map(this::mapToDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public AttendanceDTO getAttendanceByStudentAndClassroomAndDate(Long studentId, Long classroomId, LocalDate date) {
        log.info("Fetching attendance for student: {} in classroom: {} on date: {}", studentId, classroomId, date);

        Optional<Attendance> attendance = attendanceRepository.findByStudentIdAndClassroomIdAndDate(studentId, classroomId, date);
        return attendance.map(this::mapToDTO).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByStudent(Long studentId) {
        log.info("Fetching attendance for student: {}", studentId);

        List<Attendance> attendanceList = attendanceRepository.findByStudentId(studentId);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByClassroom(Long classroomId) {
        log.info("Fetching attendance for classroom: {}", classroomId);

        List<Attendance> attendanceList = attendanceRepository.findByClassroomId(classroomId);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {
        log.info("Fetching attendance for date: {}", date);

        List<Attendance> attendanceList = attendanceRepository.findByDate(date);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByClassroomAndDate(Long classroomId, LocalDate date) {
        log.info("Fetching attendance for classroom: {} on date: {}", classroomId, date);

        List<Attendance> attendanceList = attendanceRepository.findByClassroomIdAndDate(classroomId, date);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByStudentAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching attendance for student: {} between {} and {}", studentId, startDate, endDate);

        List<Attendance> attendanceList = attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByClassroomAndDateRange(Long classroomId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching attendance for classroom: {} between {} and {}", classroomId, startDate, endDate);

        List<Attendance> attendanceList = attendanceRepository.findByClassroomIdAndDateBetween(classroomId, startDate, endDate);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByStatus(AttendanceStatus status) {
        log.info("Fetching attendance by status: {}", status);

        List<Attendance> attendanceList = attendanceRepository.findByStatus(status);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByClassroomAndStatus(Long classroomId, AttendanceStatus status) {
        log.info("Fetching attendance for classroom: {} with status: {}", classroomId, status);

        List<Attendance> attendanceList = attendanceRepository.findByClassroomIdAndStatus(classroomId, status);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByStudentAndStatus(Long studentId, AttendanceStatus status) {
        log.info("Fetching attendance for student: {} with status: {}", studentId, status);

        List<Attendance> attendanceList = attendanceRepository.findByStudentIdAndStatus(studentId, status);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByMarkedBy(Long markedBy) {
        log.info("Fetching attendance marked by: {}", markedBy);

        List<Attendance> attendanceList = attendanceRepository.findByMarkedBy(markedBy);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAttendanceByTeacher(Long teacherId) {
        log.info("Fetching attendance for teacher: {}", teacherId);

        List<Attendance> attendanceList = attendanceRepository.findAttendanceByTeacher(teacherId);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getAbsentStudentsByDate(LocalDate date) {
        log.info("Fetching absent students for date: {}", date);

        List<Attendance> attendanceList = attendanceRepository.findAbsentStudentsByDate(date);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> getExcusedStudentsByDate(LocalDate date) {
        log.info("Fetching excused students for date: {}", date);

        List<Attendance> attendanceList = attendanceRepository.findExcusedStudentsByDate(date);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAttendanceByStudentAndStatus(Long studentId, AttendanceStatus status) {
        return attendanceRepository.countByStudentIdAndStatus(studentId, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countTotalAttendanceByStudent(Long studentId) {
        return attendanceRepository.countTotalAttendanceByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAttendancePercentageByStudent(Long studentId) {
        Double percentage = attendanceRepository.calculateAttendancePercentageByStudentId(studentId);
        return percentage != null ? percentage : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findStudentsWithLowAttendance(Long classroomId, Double percentage) {
        return attendanceRepository.findStudentsWithLowAttendance(classroomId, percentage);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAttendanceByClassroomAndStatus(Long classroomId, AttendanceStatus status) {
        return attendanceRepository.countByClassroomIdAndStatus(classroomId, status);
    }

    @Override
    public List<AttendanceDTO> markBulkAttendance(List<AttendanceDTO> attendanceList) {
        log.info("Marking bulk attendance for {} records", attendanceList.size());

        List<AttendanceDTO> result = new ArrayList<>();
        for (AttendanceDTO attendanceDTO : attendanceList) {
            try {
                result.add(markAttendance(attendanceDTO));
            } catch (Exception e) {
                log.warn("Failed to mark attendance for student {} on {}: {}",
                        attendanceDTO.getStudentId(), attendanceDTO.getDate(), e.getMessage());
            }
        }

        return result;
    }

    @Override
    public List<AttendanceDTO> markClassAttendance(Long classroomId, LocalDate date,
                                                   List<Long> presentStudentIds, List<Long> absentStudentIds,
                                                   List<Long> excusedStudentIds, Long markedBy) {
        log.info("Marking class attendance for classroom: {} on date: {}", classroomId, date);

        List<AttendanceDTO> result = new ArrayList<>();

        // Mark present students
        for (Long studentId : presentStudentIds) {
            try {
                result.add(markStudentPresent(studentId, classroomId, date, markedBy));
            } catch (Exception e) {
                log.warn("Failed to mark student {} as present: {}", studentId, e.getMessage());
            }
        }

        // Mark absent students
        for (Long studentId : absentStudentIds) {
            try {
                result.add(markStudentAbsent(studentId, classroomId, date, markedBy));
            } catch (Exception e) {
                log.warn("Failed to mark student {} as absent: {}", studentId, e.getMessage());
            }
        }

        // Mark excused students
        for (Long studentId : excusedStudentIds) {
            try {
                result.add(markStudentExcused(studentId, classroomId, date, markedBy));
            } catch (Exception e) {
                log.warn("Failed to mark student {} as excused: {}", studentId, e.getMessage());
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isStudentPresentOnDate(Long studentId, LocalDate date) {
        Optional<Attendance> attendance = attendanceRepository.findByStudentIdAndDate(studentId, date);
        return attendance.map(a -> a.getStatus() == AttendanceStatus.PRESENT || a.getStatus() == AttendanceStatus.EXCUSED)
                .orElse(false);
    }

    @Override
    public AttendanceDTO markStudentPresent(Long studentId, Long classroomId, LocalDate date, Long markedBy) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setStudentId(studentId);
        attendanceDTO.setClassroomId(classroomId);
        attendanceDTO.setDate(date);
        attendanceDTO.setStatus(AttendanceStatus.PRESENT);
        attendanceDTO.setMarkedBy(markedBy);

        return markAttendance(attendanceDTO);
    }

    @Override
    public AttendanceDTO markStudentAbsent(Long studentId, Long classroomId, LocalDate date, Long markedBy) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setStudentId(studentId);
        attendanceDTO.setClassroomId(classroomId);
        attendanceDTO.setDate(date);
        attendanceDTO.setStatus(AttendanceStatus.ABSENT);
        attendanceDTO.setMarkedBy(markedBy);

        return markAttendance(attendanceDTO);
    }

    @Override
    public AttendanceDTO markStudentExcused(Long studentId, Long classroomId, LocalDate date, Long markedBy) {
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setStudentId(studentId);
        attendanceDTO.setClassroomId(classroomId);
        attendanceDTO.setDate(date);
        attendanceDTO.setStatus(AttendanceStatus.EXCUSED);
        attendanceDTO.setMarkedBy(markedBy);

        return markAttendance(attendanceDTO);
    }

    private AttendanceDTO mapToDTO(Attendance attendance) {
        AttendanceDTO dto = modelMapper.map(attendance, AttendanceDTO.class);
        if (attendance.getClassroom() != null) {
            dto.setClassroomId(attendance.getClassroom().getId());
        }
        return dto;
    }
}
