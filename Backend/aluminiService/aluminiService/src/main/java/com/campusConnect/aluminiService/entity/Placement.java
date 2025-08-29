package com.campusConnect.aluminiService.entity;

import com.campusConnect.aluminiService.entity.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Placement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long studentId;

    private String Company;

    private String role;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String interviewExperience;
}