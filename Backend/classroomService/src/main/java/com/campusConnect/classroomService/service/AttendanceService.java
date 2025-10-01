package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.AttendanceDTO;
import com.campusConnect.classroomService.entity.enums.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    // CRUD Operations
    AttendanceDTO markAttendance(AttendanceDTO attendanceDTO);
    AttendanceDTO getAttendanceById(Long id);
    List<AttendanceDTO> getAllAttendance();
    AttendanceDTO updateAttendance(Long id, AttendanceDTO attendanceDTO);
    void deleteAttendance(Long id);

    // Query Operations
    AttendanceDTO getAttendanceByStudentAndDate(Long studentId, LocalDate date);
    AttendanceDTO getAttendanceByStudentAndClassroomAndDate(Long studentId, Long classroomId, LocalDate date);
    List<AttendanceDTO> getAttendanceByStudent(Long studentId);
    List<AttendanceDTO> getAttendanceByClassroom(Long classroomId);
    List<AttendanceDTO> getAttendanceByDate(LocalDate date);
    List<AttendanceDTO> getAttendanceByClassroomAndDate(Long classroomId, LocalDate date);
    List<AttendanceDTO> getAttendanceByStudentAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate);
    List<AttendanceDTO> getAttendanceByClassroomAndDateRange(Long classroomId, LocalDate startDate, LocalDate endDate);
    List<AttendanceDTO> getAttendanceByStatus(AttendanceStatus status);
    List<AttendanceDTO> getAttendanceByClassroomAndStatus(Long classroomId, AttendanceStatus status);
    List<AttendanceDTO> getAttendanceByStudentAndStatus(Long studentId, AttendanceStatus status);
    List<AttendanceDTO> getAttendanceByMarkedBy(Long markedBy);
    List<AttendanceDTO> getAttendanceByTeacher(Long teacherId);
    List<AttendanceDTO> getAbsentStudentsByDate(LocalDate date);
    List<AttendanceDTO> getExcusedStudentsByDate(LocalDate date);

    // Statistics and Analytics
    Long countAttendanceByStudentAndStatus(Long studentId, AttendanceStatus status);
    Long countTotalAttendanceByStudent(Long studentId);
    Double calculateAttendancePercentageByStudent(Long studentId);
    List<Long> findStudentsWithLowAttendance(Long classroomId, Double percentage);
    Long countAttendanceByClassroomAndStatus(Long classroomId, AttendanceStatus status);

    // Bulk Operations
    List<AttendanceDTO> markBulkAttendance(List<AttendanceDTO> attendanceList);
    List<AttendanceDTO> markClassAttendance(Long classroomId, LocalDate date, List<Long> presentStudentIds,
                                            List<Long> absentStudentIds, List<Long> excusedStudentIds, Long markedBy);

    // Utility Methods
    boolean isStudentPresentOnDate(Long studentId, LocalDate date);
    AttendanceDTO markStudentPresent(Long studentId, Long classroomId, LocalDate date, Long markedBy);
    AttendanceDTO markStudentAbsent(Long studentId, Long classroomId, LocalDate date, Long markedBy);
    AttendanceDTO markStudentExcused(Long studentId, Long classroomId, LocalDate date, Long markedBy);
}
