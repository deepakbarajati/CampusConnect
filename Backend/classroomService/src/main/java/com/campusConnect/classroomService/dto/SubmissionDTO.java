package com.campusConnect.classroomService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private Long id;

    @NotNull(message = "Assignment ID is required")
    private Long assignmentId;

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "File URL is required")
    private String file_url;

    private String grade;
}
