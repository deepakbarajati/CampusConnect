package com.campusConnect.gamificationService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

    private Long userId;

    private Long targetId;

    private Long rating;

    private String comment;

    private LocalDateTime createdAt;
}