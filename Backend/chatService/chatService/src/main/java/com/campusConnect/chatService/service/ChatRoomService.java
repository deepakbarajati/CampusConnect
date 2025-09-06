package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.ChatRoomDTO;
import jakarta.validation.Valid;

public interface ChatRoomService {
    ChatRoomDTO createChatRoom(@Valid ChatRoomDTO chatRoomDTO);
}
