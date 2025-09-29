package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.ChatRoomDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ChatRoomService {
    ChatRoomDTO createChatRoom(@Valid ChatRoomDTO chatRoomDTO);

    List<ChatRoomDTO> getAllChatRoom();

    ChatRoomDTO getChatRoomById(String chatRoomId);

    ChatRoomDTO deleteChatRoomById(String chatRoomId);

    ChatRoomDTO updateChatRoomById(String chatRoomId, @Valid ChatRoomDTO chatRoomDTO);
}
