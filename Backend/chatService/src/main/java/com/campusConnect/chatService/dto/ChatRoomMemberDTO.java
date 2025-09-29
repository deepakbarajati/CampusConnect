package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.document.enums.MemberRole;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomMemberDTO {
    private String id;

    private String chatRoomId;

    private Long userId;

    private MemberRole role;

    private LocalDateTime joinedAt;
}
