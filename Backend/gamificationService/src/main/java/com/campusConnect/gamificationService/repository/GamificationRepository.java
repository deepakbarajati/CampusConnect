package com.campusConnect.gamificationService.repository;

import com.campusConnect.gamificationService.entity.Gamification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamificationRepository extends JpaRepository<Gamification, Long> {
    List<Gamification> findByUserId(Long userId);
    Long sumPointsByUserId(Long userId);
}
