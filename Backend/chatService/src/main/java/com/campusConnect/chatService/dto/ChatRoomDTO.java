package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.document.enums.ChatType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

    private String id;

    private String name;

    private Long createdBy;

    @NotNull(message = "ChatType is Required")
    private ChatType type;

}
