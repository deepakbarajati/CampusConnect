package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.document.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {
    private String id;

    @NotNull(message = "ChatRoom's Id is mandatory")
    private String chatRoomId;

    @NotNull(message = "Sender's Id is mandatory")
    private Long senderId;

    private Long receiverId;

    @NotBlank(message = "Message content cannot be blank")
    private String content;

    @NotNull(message = "Message type is mandatory")
    private MessageType type;

    private LocalDateTime sentAt;

    private Boolean isRead = false; // default false
}
