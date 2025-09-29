package com.campusConnect.aluminiService.dto;

import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentorshipDTO {

    private Long id;

    @NotNull(message = "Mentor ID is required")
    private Long mentorId;

    @NotNull(message = "Mentee ID is required")
    private Long menteeId;

    @NotNull(message = "Start date is required")
    private Date startDate;

    private Date endDate;

    @NotNull(message = "Status is required")
    private MentorShipStatus status;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 200, message = "Title must be between 5 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private List<String> goals;

    private String meetingFrequency;

    private String meetingMode;

    private Integer sessionCount;

    @DecimalMin(value = "1.0", message = "Rating must be at least 1.0")
    @DecimalMax(value = "5.0", message = "Rating must not exceed 5.0")
    private Double feedbackRating;

    private Date createdAt;

    private Date updatedAt;
}
