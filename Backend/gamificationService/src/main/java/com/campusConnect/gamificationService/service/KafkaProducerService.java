package com.campusConnect.gamificationService.service;

import com.campusConnect.gamificationService.event.XpGainedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendXpGainedEvent(XpGainedEvent event) {
        kafkaTemplate.send("xp-gained", event);
        log.info("Sent XP gained event: {}", event);
    }
}
