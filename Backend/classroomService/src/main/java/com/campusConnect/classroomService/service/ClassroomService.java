package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.ClassroomDTO;

import java.util.List;

public interface ClassroomService {

    // CRUD Operations
    ClassroomDTO createClassroom(ClassroomDTO classroomDTO);
    ClassroomDTO getClassroomById(Long id);
    List<ClassroomDTO> getAllClassrooms();
    ClassroomDTO updateClassroom(Long id, ClassroomDTO classroomDTO);
    void deleteClassroom(Long id);

    // Query Operations
    List<ClassroomDTO> getClassroomsByTeacher(Long teacherId);
    List<ClassroomDTO> getClassroomsBySemester(Integer semester);
    List<ClassroomDTO> searchClassroomsBySubject(String subject);
    List<ClassroomDTO> getClassroomsByTeacherAndSemester(Long teacherId, Integer semester);
    List<ClassroomDTO> getClassroomsWithSchedule();
    List<ClassroomDTO> getClassroomsWithoutSchedule();
    List<ClassroomDTO> getClassroomsBySubjectAndSemester(String subject, Integer semester);

    // Schedule Management
    ClassroomDTO assignSchedule(Long classroomId, Long scheduleId);
    ClassroomDTO removeSchedule(Long classroomId);
}
