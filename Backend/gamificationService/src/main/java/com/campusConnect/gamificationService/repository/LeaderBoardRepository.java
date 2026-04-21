package com.campusConnect.gamificationService.repository;

import com.campusConnect.gamificationService.entity.LeaderBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderBoardRepository extends JpaRepository<LeaderBoard, Long> {
    @Query("SELECT l FROM LeaderBoard l ORDER BY l.points DESC")
    List<LeaderBoard> findAllOrderByPointsDesc();
}
