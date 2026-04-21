package com.campusConnect.gamificationService.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XpGainedEvent {
    private Long userId;
    private int xpAmount;
    private String reason;
}
