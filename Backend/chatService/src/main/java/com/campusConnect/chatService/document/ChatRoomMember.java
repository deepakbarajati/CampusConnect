package com.campusConnect.chatService.document;

import com.campusConnect.chatService.document.enums.MemberRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "chat_room_members")
public class ChatRoomMember {
    @Id
    private String id; // Use String for MongoDB ObjectId

    private String chatRoomId; // Store ChatRoom's ID

    private Long userId;

    private MemberRole role; // MongoDB stores enum as string

    private LocalDateTime joinedAt; // Set manually in your service (no @CreationTimestamp in MongoDB)
}
