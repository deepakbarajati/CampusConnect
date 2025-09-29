package com.campusConnect.aluminiService.dto;

import com.campusConnect.aluminiService.nodes.enums.Mode;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumniMeetDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @NotNull(message = "Mode is required")
    private Mode mode;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    private String location;

    private String meetingLink;

    @NotNull(message = "Scheduled date and time is required")
    @Future(message = "Scheduled date and time must be in the future")
    private LocalDateTime scheduledDateTime;

    @Positive(message = "Duration must be positive")
    private Integer duration;

    @Positive(message = "Max participants must be positive")
    private Integer maxParticipants;

    private String status;

    @NotNull(message = "Organizer ID is required")
    private Long organizerId;

    private List<Long> participantIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<String> tags;
}
