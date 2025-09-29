package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO createMessage(MessageDTO messageDTO);

    MessageDTO updateMessage(String id, MessageDTO messageDTO);

    void deleteMessage(String id);

    List<MessageDTO> getAllMessages();

    List<MessageDTO> getAllMessagesByUser(Long userId);

    MessageDTO getMessageById(String id);

    MessageDTO markMessageAsRead(String id);

    List<MessageDTO> markAllMessagesAsRead(Long userId, String chatRoomId);
}
