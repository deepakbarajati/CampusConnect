package com.campusConnect.gamificationService.entity;

import com.campusConnect.gamificationService.entity.enums.BadgeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "gamification")
public class Gamification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long points;


    private LocalDate earnedAt;
    //TODO add achievement and badgeTags on profile create an new entity badge
}