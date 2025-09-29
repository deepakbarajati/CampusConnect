package com.campusConnect.aluminiService.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private Long id;

    @NotNull(message = "Posted by user ID is required")
    private Long postedBy;

    @NotBlank(message = "Job title is required")
    @Size(min = 5, max = 200, message = "Job title must be between 5 and 200 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 50, max = 5000, message = "Description must be between 50 and 5000 characters")
    private String description;

    @NotBlank(message = "Company name is required")
    private String company;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Apply link is required")
    @Pattern(regexp = "^https?://.*", message = "Apply link must be a valid URL")
    private String applyLink;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    private LocalDateTime deadLine;

    private String jobType;

    private String salaryRange;

    private String experienceLevel;

    private List<String> skills;

    private Boolean isRemote;

    private String status;

    private Integer applicationCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
