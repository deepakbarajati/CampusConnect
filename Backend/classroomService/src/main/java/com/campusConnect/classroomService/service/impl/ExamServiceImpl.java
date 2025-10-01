package com.campusConnect.classroomService.service.impl;

import com.campusConnect.classroomService.dto.ExamDTO;
import com.campusConnect.classroomService.entity.Classroom;
import com.campusConnect.classroomService.entity.Exam;
import com.campusConnect.classroomService.entity.enums.ExamType;
import com.campusConnect.classroomService.repository.ClassroomRepository;
import com.campusConnect.classroomService.repository.ExamRepository;
import com.campusConnect.classroomService.service.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final ClassroomRepository classroomRepository;
    private final ModelMapper modelMapper;

    @Override
    public ExamDTO createExam(ExamDTO examDTO) {
        log.info("Creating new exam for classroom: {} with type: {} on date: {}",
                examDTO.getClassroomId(), examDTO.getType(), examDTO.getDate());

        // Verify classroom exists
        Classroom classroom = classroomRepository.findById(examDTO.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + examDTO.getClassroomId()));

        Exam exam = modelMapper.map(examDTO, Exam.class);
        exam.setClassroom(classroom);

        Exam savedExam = examRepository.save(exam);
        log.info("Exam created successfully with ID: {}", savedExam.getId());

        return mapToDTO(savedExam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamDTO getExamById(Long id) {
        log.info("Fetching exam with ID: {}", id);

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));

        return mapToDTO(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getAllExams() {
        log.info("Fetching all exams");

        List<Exam> exams = examRepository.findAll();
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        log.info("Updating exam with ID: {}", id);

        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));

        // Update classroom if changed
        if (!existingExam.getClassroom().getId().equals(examDTO.getClassroomId())) {
            Classroom classroom = classroomRepository.findById(examDTO.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found with ID: " + examDTO.getClassroomId()));
            existingExam.setClassroom(classroom);
        }

        // Update other fields
        existingExam.setType(examDTO.getType());
        existingExam.setDate(examDTO.getDate());
        existingExam.setMarks(examDTO.getMarks());

        Exam updatedExam = examRepository.save(existingExam);
        log.info("Exam updated successfully with ID: {}", updatedExam.getId());

        return mapToDTO(updatedExam);
    }

    @Override
    public void deleteExam(Long id) {
        log.info("Deleting exam with ID: {}", id);

        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with ID: " + id);
        }

        examRepository.deleteById(id);
        log.info("Exam deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByClassroom(Long classroomId) {
        log.info("Fetching exams for classroom: {}", classroomId);

        List<Exam> exams = examRepository.findByClassroomId(classroomId);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByType(ExamType type) {
        log.info("Fetching exams by type: {}", type);

        List<Exam> exams = examRepository.findByType(type);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDate(Date date) {
        log.info("Fetching exams for date: {}", date);

        List<Exam> exams = examRepository.findByDate(date);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByClassroomAndType(Long classroomId, ExamType type) {
        log.info("Fetching exams for classroom: {} with type: {}", classroomId, type);

        List<Exam> exams = examRepository.findByClassroomIdAndType(classroomId, type);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByClassroomAndDate(Long classroomId, Date date) {
        log.info("Fetching exams for classroom: {} on date: {}", classroomId, date);

        List<Exam> exams = examRepository.findByClassroomIdAndDate(classroomId, date);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByTypeAndDate(ExamType type, Date date) {
        log.info("Fetching exams with type: {} on date: {}", type, date);

        List<Exam> exams = examRepository.findByTypeAndDate(type, date);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDateRange(Date startDate, Date endDate) {
        log.info("Fetching exams between {} and {}", startDate, endDate);

        List<Exam> exams = examRepository.findByDateBetween(startDate, endDate);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByClassroomAndDateRange(Long classroomId, Date startDate, Date endDate) {
        log.info("Fetching exams for classroom: {} between {} and {}", classroomId, startDate, endDate);

        List<Exam> exams = examRepository.findByClassroomIdAndDateBetween(classroomId, startDate, endDate);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByMarksRange(Float minMarks, Float maxMarks) {
        log.info("Fetching exams with marks between {} and {}", minMarks, maxMarks);

        List<Exam> exams = examRepository.findByMarksBetween(minMarks, maxMarks);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByClassroomAndMarksRange(Long classroomId, Float minMarks, Float maxMarks) {
        log.info("Fetching exams for classroom: {} with marks between {} and {}", classroomId, minMarks, maxMarks);

        List<Exam> exams = examRepository.findByClassroomIdAndMarksBetween(classroomId, minMarks, maxMarks);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDateBefore(Date date) {
        log.info("Fetching exams before date: {}", date);

        List<Exam> exams = examRepository.findByDateBefore(date);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByDateAfter(Date date) {
        log.info("Fetching exams after date: {}", date);

        List<Exam> exams = examRepository.findByDateAfter(date);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsByTeacher(Long teacherId) {
        log.info("Fetching exams for teacher: {}", teacherId);

        List<Exam> exams = examRepository.findExamsByTeacher(teacherId);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsBySubject(String subject) {
        log.info("Fetching exams for subject: {}", subject);

        List<Exam> exams = examRepository.findExamsBySubject(subject);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsBySemester(Integer semester) {
        log.info("Fetching exams for semester: {}", semester);

        List<Exam> exams = examRepository.findExamsBySemester(semester);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsWithMarksAbove(Float threshold) {
        log.info("Fetching exams with marks above: {}", threshold);

        List<Exam> exams = examRepository.findExamsWithMarksAbove(threshold);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsWithMarksBelow(Float threshold) {
        log.info("Fetching exams with marks below: {}", threshold);

        List<Exam> exams = examRepository.findExamsWithMarksBelow(threshold);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getUpcomingExams() {
        log.info("Fetching upcoming exams");

        List<Exam> exams = examRepository.findUpcomingExams(new Date());
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getPastExams() {
        log.info("Fetching past exams");

        List<Exam> exams = examRepository.findPastExams(new Date());
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageMarksByClassroom(Long classroomId) {
        Double average = examRepository.calculateAverageMarksByClassroom(classroomId);
        return average != null ? average : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateAverageMarksByType(ExamType type) {
        Double average = examRepository.calculateAverageMarksByType(type);
        return average != null ? average : 0.0;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countExamsByClassroom(Long classroomId) {
        return examRepository.countExamsByClassroom(classroomId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countExamsByType(ExamType type) {
        return examRepository.countExamsByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsForToday() {
        log.info("Fetching exams for today");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date startOfDay = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date startOfNextDay = cal.getTime();

        List<Exam> exams = examRepository.findByDateBetween(startOfDay, startOfNextDay);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsForWeek() {
        log.info("Fetching exams for this week");

        Calendar cal = Calendar.getInstance();
        Date endDate = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        Date startDate = cal.getTime();

        List<Exam> exams = examRepository.findByDateBetween(endDate, startDate);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExamDTO> getExamsForMonth() {
        log.info("Fetching exams for this month");

        Calendar cal = Calendar.getInstance();
        Date endDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date startDate = cal.getTime();

        List<Exam> exams = examRepository.findByDateBetween(endDate, startDate);
        return exams.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ExamDTO mapToDTO(Exam exam) {
        ExamDTO dto = modelMapper.map(exam, ExamDTO.class);
        if (exam.getClassroom() != null) {
            dto.setClassroomId(exam.getClassroom().getId());
        }
        return dto;
    }
}
