package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberService {
    ChatRoomMemberDTO chatRoomMemberCreation(@Valid ChatRoomMemberDTO chatRoomMemberDTO);

    List<ChatRoomMemberDTO> getAllChatRoomMember();

    ChatRoomMemberDTO getChatRoomMemberById(Long chatRoomMemberId);

    ChatRoomMemberDTO deleteChatRoomMemberById(Long chatRoomMemberId);

    ChatRoomMemberDTO updateChatRoomMemberById(Long chatRoomMemberId, @Valid ChatRoomMemberDTO chatRoomMemberDTO);

    List<ChatRoomMemberDTO> getAllChatRoomMemberByChatRoomId(Long chatRoomId);
}
