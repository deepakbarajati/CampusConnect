package com.campusConnect.chatService.document;

import com.campusConnect.chatService.document.enums.MessageType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "attachments")
public class Attachment {
    @Id
    private String id; // Use String for MongoDB ObjectId

    private String messageId; // Store related message's ID

    private String fileUrl;

    private MessageType type; // MongoDB stores enums as strings by default

    private LocalDateTime uploadedAt;
}
