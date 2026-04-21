package com.campusConnect.gamificationService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderBoardDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    private BigInteger rank;

    @NotNull(message = "Points are required")
    private Integer points;
}
