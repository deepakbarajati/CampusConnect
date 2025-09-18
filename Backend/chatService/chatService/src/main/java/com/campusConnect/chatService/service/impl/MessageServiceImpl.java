package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.dto.MessageDTO;
import com.campusConnect.chatService.entity.ChatRoom;
import com.campusConnect.chatService.entity.Message;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.repository.MessageRepository;
import com.campusConnect.chatService.service.MessageService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MapperUtils mapperUtils;

    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(messageDTO.getChatRoomId())
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        Message message = mapperUtils.map(messageDTO, Message.class);
        message.setChatRoom(chatRoom);
        message.setIsRead(false);

        return mapperUtils.map(messageRepository.save(message), MessageDTO.class);
    }

    @Override
    public MessageDTO updateMessage(Long id, MessageDTO messageDTO) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        message.setContent(messageDTO.getContent());
        message.setIsRead(messageDTO.getIsRead());
        message.setType(messageDTO.getType());

        return mapperUtils.map(messageRepository.save(message), MessageDTO.class);
    }

    @Override
    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message not found");
        }
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDTO> getAllMessages() {
        return messageRepository.findAll()
                .stream()
                .map(msg -> mapperUtils.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getAllMessagesByUser(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId)
                .stream()
                .map(msg -> mapperUtils.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageDTO getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return mapperUtils.map(message, MessageDTO.class);
    }

    @Override
    public MessageDTO markMessageAsRead(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setIsRead(true);
        return mapperUtils.map(messageRepository.save(message), MessageDTO.class);
    }

    @Override
    public List<MessageDTO> markAllMessagesAsRead(Long userId, Long chatRoomId) {
        List<Message> messages = messageRepository.findByChatRoomId(chatRoomId);

        messages.stream()
                .filter(msg -> msg.getReceiverId().equals(userId) && Boolean.FALSE.equals(msg.getIsRead()))
                .forEach(msg -> msg.setIsRead(true));

        List<Message> updatedMessages = messageRepository.saveAll(messages);

        return updatedMessages.stream()
                .map(msg -> mapperUtils.map(msg, MessageDTO.class))
                .collect(Collectors.toList());
    }
}
