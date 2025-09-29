package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberService {
    ChatRoomMemberDTO chatRoomMemberCreation(@Valid ChatRoomMemberDTO chatRoomMemberDTO);

    List<ChatRoomMemberDTO> getAllChatRoomMember();

    ChatRoomMemberDTO getChatRoomMemberById(String chatRoomMemberId);

    ChatRoomMemberDTO deleteChatRoomMemberById(String chatRoomMemberId);

    ChatRoomMemberDTO updateChatRoomMemberById(String chatRoomMemberId, @Valid ChatRoomMemberDTO chatRoomMemberDTO);

    List<ChatRoomMemberDTO> getAllChatRoomMemberByChatRoomId(String chatRoomId);
}
