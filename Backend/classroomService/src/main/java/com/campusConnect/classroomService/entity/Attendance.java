package com.campusConnect.classroomService.entity;

import com.campusConnect.classroomService.entity.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long studentId; // Fixed naming convention

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private LocalDate date; // Using LocalDate instead of Date

    private Boolean isPresent;

    // Enhanced fields
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // PRESENT, ABSENT, EXCUSED

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long markedBy; // ID of person who marked attendance (teacher/admin)
}
