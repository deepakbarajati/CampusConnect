package com.campusConnect.aluminiService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long postedBy;

    private String description;

    private String company;

    private String location;

    private String applyLink;

    private LocalDateTime deadLine;
}