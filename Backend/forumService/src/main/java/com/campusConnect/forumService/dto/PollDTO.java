package com.campusConnect.forumService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollDTO {
    private Long id;

    @NotBlank(message = "Question is required")
    private String question;

    @NotNull(message = "Created by is required")
    private Long createdBy;

    private Long forumId;

    private List<String> options;

    private List<PollVoteDTO> votes;
}
