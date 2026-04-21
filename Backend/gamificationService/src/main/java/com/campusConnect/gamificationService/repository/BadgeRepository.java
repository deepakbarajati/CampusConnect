package com.campusConnect.gamificationService.repository;

import com.campusConnect.gamificationService.entity.Badge;
import com.campusConnect.gamificationService.entity.enums.BadgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByBadgeType(BadgeType badgeType);
}
