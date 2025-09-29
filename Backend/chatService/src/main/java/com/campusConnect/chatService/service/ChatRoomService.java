package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.ChatRoomDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ChatRoomService {
    ChatRoomDTO createChatRoom(@Valid ChatRoomDTO chatRoomDTO);

    List<ChatRoomDTO> getAllChatRoom();

    ChatRoomDTO getChatRoomById(Long chatRoomId);

    ChatRoomDTO deleteChatRoomById(Long chatRoomId);

    ChatRoomDTO updateChatRoomById(Long chatRoomId, @Valid ChatRoomDTO chatRoomDTO);
}
