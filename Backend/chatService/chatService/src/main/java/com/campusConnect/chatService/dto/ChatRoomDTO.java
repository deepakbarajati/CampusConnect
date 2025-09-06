package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.entity.enums.ChatType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {

    private String name;

    @NotNull(message = "ChatType is Required")
    private ChatType type;

    @NotNull(message = "createdBy is required to create an chatRoom")
    private Long createdBy;

}
