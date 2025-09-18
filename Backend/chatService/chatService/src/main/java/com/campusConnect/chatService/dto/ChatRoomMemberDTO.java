package com.campusConnect.chatService.dto;

import com.campusConnect.chatService.entity.enums.MemberRole;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomMemberDTO {
    private Long id;

    private Long chatRoomId;

    private Long userId;

    private MemberRole role;

    private LocalDateTime joinedAt;
}
