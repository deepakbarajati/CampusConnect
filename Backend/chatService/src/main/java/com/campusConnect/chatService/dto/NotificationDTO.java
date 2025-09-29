package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.document.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationDTO {

    private String id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotNull(message = "Notification type cannot be null")
    private NotificationType type;

    private String link; // optional

    private boolean isRead;

    private LocalDateTime timestamp;
}
