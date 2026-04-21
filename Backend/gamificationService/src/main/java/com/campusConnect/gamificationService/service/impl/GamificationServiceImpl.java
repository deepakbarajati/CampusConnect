package com.campusConnect.gamificationService.service.impl;

import com.campusConnect.gamificationService.dto.GamificationDTO;
import com.campusConnect.gamificationService.dto.LeaderBoardDTO;
import com.campusConnect.gamificationService.entity.Gamification;
import com.campusConnect.gamificationService.entity.LeaderBoard;
import com.campusConnect.gamificationService.repository.GamificationRepository;
import com.campusConnect.gamificationService.repository.LeaderBoardRepository;
import com.campusConnect.gamificationService.service.GamificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class GamificationServiceImpl implements GamificationService {

    private final GamificationRepository gamificationRepository;
    private final LeaderBoardRepository leaderBoardRepository;
    private final ModelMapper modelMapper;

    @Override
    public GamificationDTO addPoints(GamificationDTO gamificationDTO) {
        Gamification gamification = modelMapper.map(gamificationDTO, Gamification.class);
        gamification.setEarnedAt(LocalDate.now());
        Gamification saved = gamificationRepository.save(gamification);
        updateLeaderboard();
        return modelMapper.map(saved, GamificationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GamificationDTO> getUserPoints(Long userId) {
        return gamificationRepository.findByUserId(userId).stream()
                .map(g -> modelMapper.map(g, GamificationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalPoints(Long userId) {
        Long total = gamificationRepository.sumPointsByUserId(userId);
        return total != null ? total : 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaderBoardDTO> getLeaderboard() {
        return leaderBoardRepository.findAllOrderByPointsDesc().stream()
                .map(l -> modelMapper.map(l, LeaderBoardDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updateLeaderboard() {
        // Clear existing leaderboard
        leaderBoardRepository.deleteAll();

        // Recalculate from gamification data
        List<Object[]> results = gamificationRepository.findAll().stream()
                .collect(Collectors.groupingBy(Gamification::getUserId,
                        Collectors.summingLong(Gamification::getPoints)))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(e -> new Object[]{e.getKey(), e.getValue()})
                .collect(Collectors.toList());

        for (int i = 0; i < results.size(); i++) {
            Object[] result = results.get(i);
            LeaderBoard leaderBoard = new LeaderBoard();
            leaderBoard.setUserId((Long) result[0]);
            leaderBoard.setPoints(((Long) result[1]).intValue());
            leaderBoard.setRank(BigInteger.valueOf(i + 1));
            leaderBoardRepository.save(leaderBoard);
        }
    }
}
