package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.entity.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDTO {

    @NotNull(message = "ChatRoom's Id is mandatory")
    private Long chatRoomId;

    @NotNull(message = "Sender's Id is mandatory")
    private Long senderId;

    @NotNull(message = "Sender's Id is mandatory")
    private Long receiverId;

    @NotBlank(message = "Message field can not be blank")
    private String content;

    private MessageType type;
    private LocalDateTime sendAt;

    private Boolean isRead;
}
