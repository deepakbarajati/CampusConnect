package com.campusConnect.classroomService.dto;

import com.campusConnect.classroomService.entity.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Classroom ID is required")
    private Long classroomId;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long markedBy;
}
