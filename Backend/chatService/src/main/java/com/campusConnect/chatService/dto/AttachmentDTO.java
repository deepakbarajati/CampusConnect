package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.document.enums.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDTO {

    private Long id;

    @NotNull(message = "Message ID cannot be null")
    private Long messageId;

    private String fileUrl;

    @NotNull(message = "Message type cannot be null")
    private MessageType type;
}
