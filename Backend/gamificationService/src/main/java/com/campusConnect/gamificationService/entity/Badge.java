package com.campusConnect.gamificationService.entity;

import com.campusConnect.gamificationService.entity.enums.BadgeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BadgeType badgeType;
}
