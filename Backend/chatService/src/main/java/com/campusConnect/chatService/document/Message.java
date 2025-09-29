package com.campusConnect.chatService.document;

import com.campusConnect.chatService.document.enums.MessageType;
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
@Document(collection = "messages")
public class Message {
    @Id
    private String id; // MongoDB ObjectId as String

    private String chatRoomId; // Reference to chat room by its ID

    private Long senderId;

    private Long receiverId; // Not null for private messages

    private String content;

    private MessageType type; // Enum field

    private LocalDateTime sentAt; // Set manually in your service layer

    private Boolean isRead;
}
