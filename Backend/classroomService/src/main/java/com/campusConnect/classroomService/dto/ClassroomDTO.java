package com.campusConnect.classroomService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDTO {

    private Long id;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotNull(message = "Semester is required")
    @Positive(message = "Semester must be positive")
    private Integer semester;

    private Long scheduleId; // Reference to ClassroomSchedule ID

    private String description;
}
