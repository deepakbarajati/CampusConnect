package com.campusConnect.gamificationService.service;

import com.campusConnect.gamificationService.event.XpGainedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final GamificationService gamificationService;

    @KafkaListener(topics = "xp-gained", groupId = "gamification-group")
    public void consumeXpGainedEvent(XpGainedEvent event) {
        log.info("Consumed XP gained event: {}", event);
        // Process the event, e.g., update user XP
        gamificationService.addPoints(new com.campusConnect.gamificationService.dto.GamificationDTO(
                null, event.getUserId(), (long) event.getXpAmount(), null));
    }
}
