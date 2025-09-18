package com.campusConnect.chatService.service.impl;


import com.campusConnect.chatService.client.UserClient;
import com.campusConnect.chatService.dto.ChatRoomDTO;
import com.campusConnect.chatService.entity.ChatRoom;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.ChatRoomMemberRepository;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.service.ChatRoomService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserClient userClient;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MapperUtils mapperUtils;

    @Override
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO) {
        if (!userClient.userExist(chatRoomDTO.getCreatedBy())) {
            throw new ResourceNotFoundException("User not exist with this UserId: " + chatRoomDTO.getCreatedBy());
        }

        ChatRoom chatRoom = mapperUtils.map(chatRoomDTO, ChatRoom.class);
        chatRoom = chatRoomRepository.save(chatRoom);

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public List<ChatRoomDTO> getAllChatRoom() {
        return chatRoomRepository.findAll()
                .stream()
                .map(element -> mapperUtils.map(element, ChatRoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoomDTO getChatRoomById(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO deleteChatRoomById(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        chatRoomMemberRepository.deleteByChatRoomId(chatRoomId);
        chatRoomRepository.delete(chatRoom);

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO updateChatRoomById(Long chatRoomId, ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        chatRoom.setName(chatRoomDTO.getName());
        chatRoom.setType(chatRoomDTO.getType());

        chatRoom = chatRoomRepository.save(chatRoom);

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }
}
