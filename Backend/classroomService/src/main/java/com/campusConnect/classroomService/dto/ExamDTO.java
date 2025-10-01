package com.campusConnect.classroomService.dto;

import com.campusConnect.classroomService.entity.enums.ExamType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {

    private Long id;

    @NotNull(message = "Classroom ID is required")
    private Long classroomId;

    @NotNull(message = "Exam type is required")
    private ExamType type;

    @NotNull(message = "Exam date is required")
    private Date date;

    @DecimalMin(value = "0.0", message = "Marks cannot be negative")
    @DecimalMax(value = "100.0", message = "Marks cannot exceed 100")
    private Float marks;
}
