package com.campusConnect.gamificationService.service;

import com.campusConnect.gamificationService.dto.GamificationDTO;
import com.campusConnect.gamificationService.dto.LeaderBoardDTO;

import java.util.List;

public interface GamificationService {
    // Gamification operations
    GamificationDTO addPoints(GamificationDTO gamificationDTO);
    List<GamificationDTO> getUserPoints(Long userId);
    Long getTotalPoints(Long userId);

    // Leaderboard operations
    List<LeaderBoardDTO> getLeaderboard();
    void updateLeaderboard();
}
