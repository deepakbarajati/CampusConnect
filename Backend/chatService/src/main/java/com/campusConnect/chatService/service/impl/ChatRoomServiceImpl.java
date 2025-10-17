package com.campusConnect.chatService.service.impl;


import com.campusConnect.chatService.document.enums.MemberRole;
import com.campusConnect.chatService.dto.ChatRoomDTO;
import com.campusConnect.chatService.document.ChatRoom;
import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.ChatRoomMemberRepository;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.service.ChatRoomMemberService;
import com.campusConnect.chatService.service.ChatRoomService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.campusConnect.chatService.auth.UserContextHolder.getCurrentUserId;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final MapperUtils mapperUtils;
    private final ChatRoomMemberService chatRoomMemberService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO) {
//        try {
            // Use consistent mapper throughout
            ChatRoom chatRoom = mapperUtils.map(chatRoomDTO, ChatRoom.class);

            log.info("Current user Id: {}",getCurrentUserId());
            chatRoom.setCreatedBy(getCurrentUserId());

            log.info("User saved in chatroom");
            // Save chat room first
            chatRoom = chatRoomRepository.save(chatRoom);
            log.info("raja");

            // Create member DTO for creator
            ChatRoomMemberDTO chatRoomMemberDTO = ChatRoomMemberDTO.builder()
                    .userId(getCurrentUserId())
                    .chatRoomId(chatRoom.getId())
                    .role(MemberRole.ADMIN)
                    .joinedAt(LocalDateTime.now())
                    .build();

            log.info("Raja iranai");
            // Let service handle member creation completely - REMOVE redundant save
            chatRoomMemberService.chatRoomMemberCreation(chatRoomMemberDTO);

            log.info("Success");
            return mapperUtils.map(chatRoom, ChatRoomDTO.class);

//        } catch (Exception e) {
//            log.error("Failed to create chat room: {}", e.getMessage());
//            throw new RuntimeException("Failed to create chat room", e);
//        }
    }



    @Override
    public List<ChatRoomDTO> getAllChatRoom() {
        return chatRoomRepository.findAll()
                .stream()
                .map(element -> mapperUtils.map(element, ChatRoomDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoomDTO getChatRoomById(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO deleteChatRoomById(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        chatRoomMemberRepository.deleteByChatRoomId(chatRoomId);
        chatRoomRepository.delete(chatRoom);

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO updateChatRoomById(String chatRoomId, ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoom Not found with ID: " + chatRoomId));

        chatRoom.setName(chatRoomDTO.getName());
        chatRoom.setType(chatRoomDTO.getType());

        chatRoom = chatRoomRepository.save(chatRoom);

        return mapperUtils.map(chatRoom, ChatRoomDTO.class);
    }
}
