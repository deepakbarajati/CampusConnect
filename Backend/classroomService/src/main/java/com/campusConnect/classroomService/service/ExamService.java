package com.campusConnect.classroomService.service;

import com.campusConnect.classroomService.dto.ExamDTO;
import com.campusConnect.classroomService.entity.enums.ExamType;

import java.util.Date;
import java.util.List;

public interface ExamService {

    // CRUD Operations
    ExamDTO createExam(ExamDTO examDTO);
    ExamDTO getExamById(Long id);
    List<ExamDTO> getAllExams();
    ExamDTO updateExam(Long id, ExamDTO examDTO);
    void deleteExam(Long id);

    // Query Operations
    List<ExamDTO> getExamsByClassroom(Long classroomId);
    List<ExamDTO> getExamsByType(ExamType type);
    List<ExamDTO> getExamsByDate(Date date);
    List<ExamDTO> getExamsByClassroomAndType(Long classroomId, ExamType type);
    List<ExamDTO> getExamsByClassroomAndDate(Long classroomId, Date date);
    List<ExamDTO> getExamsByTypeAndDate(ExamType type, Date date);
    List<ExamDTO> getExamsByDateRange(Date startDate, Date endDate);
    List<ExamDTO> getExamsByClassroomAndDateRange(Long classroomId, Date startDate, Date endDate);
    List<ExamDTO> getExamsByMarksRange(Float minMarks, Float maxMarks);
    List<ExamDTO> getExamsByClassroomAndMarksRange(Long classroomId, Float minMarks, Float maxMarks);
    List<ExamDTO> getExamsByDateBefore(Date date);
    List<ExamDTO> getExamsByDateAfter(Date date);
    List<ExamDTO> getExamsByTeacher(Long teacherId);
    List<ExamDTO> getExamsBySubject(String subject);
    List<ExamDTO> getExamsBySemester(Integer semester);
    List<ExamDTO> getExamsWithMarksAbove(Float threshold);
    List<ExamDTO> getExamsWithMarksBelow(Float threshold);
    List<ExamDTO> getUpcomingExams();
    List<ExamDTO> getPastExams();

    // Statistics and Analytics
    Double calculateAverageMarksByClassroom(Long classroomId);
    Double calculateAverageMarksByType(ExamType type);
    Long countExamsByClassroom(Long classroomId);
    Long countExamsByType(ExamType type);

    // Utility Methods
    List<ExamDTO> getExamsForToday();
    List<ExamDTO> getExamsForWeek();
    List<ExamDTO> getExamsForMonth();
}
