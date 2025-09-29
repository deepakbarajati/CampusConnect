package com.campusConnect.aluminiService.dto;

import com.campusConnect.aluminiService.nodes.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDTO {
    private Long id;

    @NotNull
    private Long studentId;

    @NotBlank
    private String company;

    @NotBlank
    private String role;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salary;

    @NotNull
    private Status status;

    private String interviewExperience;

    private LocalDateTime offerDate;
    private LocalDateTime joiningDate;
    private String location;
    private String offerType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
