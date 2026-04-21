package com.campusConnect.forumService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollVoteDTO {
    private Long id;

    private Long pollId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Option index is required")
    private Integer optionIndex;
}
