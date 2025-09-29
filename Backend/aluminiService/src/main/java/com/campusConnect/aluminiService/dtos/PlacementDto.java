package com.campusConnect.aluminiService.dtos;

import com.campusConnect.aluminiService.nodes.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlacementDto {
    private Long id;

   private  Long studentId;

    private String role;

    private BigDecimal salary;

    private Status status;

    private String interviewExperience;
}