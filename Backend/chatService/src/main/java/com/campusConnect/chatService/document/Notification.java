package com.campusConnect.chatService.document;

import com.campusConnect.chatService.document.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id; // MongoDB ObjectId

    private Long userId;

    private String message;

    private NotificationType type; // Enum stored as string

    private String link; // Optional: link to resource

    private boolean isRead;

    private LocalDateTime timestamp; // Set in service/controller layer
}
