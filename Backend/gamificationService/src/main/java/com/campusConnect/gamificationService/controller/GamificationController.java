package com.campusConnect.gamificationService.controller;

import com.campusConnect.gamificationService.dto.GamificationDTO;
import com.campusConnect.gamificationService.dto.LeaderBoardDTO;
import com.campusConnect.gamificationService.service.GamificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamification")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class GamificationController {

    private final GamificationService gamificationService;

    // Gamification endpoints
    @PostMapping("/points")
    public ResponseEntity<GamificationDTO> addPoints(@Valid @RequestBody GamificationDTO gamificationDTO) {
        log.info("Adding points for user: {}", gamificationDTO.getUserId());
        GamificationDTO created = gamificationService.addPoints(gamificationDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/points/user/{userId}")
    public ResponseEntity<List<GamificationDTO>> getUserPoints(@PathVariable Long userId) {
        List<GamificationDTO> points = gamificationService.getUserPoints(userId);
        return ResponseEntity.ok(points);
    }

    @GetMapping("/points/total/{userId}")
    public ResponseEntity<Long> getTotalPoints(@PathVariable Long userId) {
        Long total = gamificationService.getTotalPoints(userId);
        return ResponseEntity.ok(total);
    }

    // Leaderboard endpoints
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderBoardDTO>> getLeaderboard() {
        List<LeaderBoardDTO> leaderboard = gamificationService.getLeaderboard();
        return ResponseEntity.ok(leaderboard);
    }

    @PostMapping("/leaderboard/update")
    public ResponseEntity<String> updateLeaderboard() {
        gamificationService.updateLeaderboard();
        return ResponseEntity.ok("Leaderboard updated");
    }
}
