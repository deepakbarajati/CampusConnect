package com.campusConnect.chatService.service;

import com.campusConnect.chatService.dto.MessageDTO;

import java.util.List;

public interface MessageService {

    MessageDTO createMessage(MessageDTO messageDTO);

    MessageDTO updateMessage(Long id, MessageDTO messageDTO);

    void deleteMessage(Long id);

    List<MessageDTO> getAllMessages();

    List<MessageDTO> getAllMessagesByUser(Long userId);

    MessageDTO getMessageById(Long id);

    MessageDTO markMessageAsRead(Long id);

    List<MessageDTO> markAllMessagesAsRead(Long userId, Long chatRoomId);
}
