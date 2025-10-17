package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.client.UserClient;
import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import com.campusConnect.chatService.document.ChatRoomMember;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.ChatRoomMemberRepository;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.service.ChatRoomMemberService;
import com.campusConnect.chatService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserClient userClient;
    private final MapperUtils mapperUtils;


    @Override
    @Transactional
    public ChatRoomMemberDTO chatRoomMemberCreation(ChatRoomMemberDTO chatRoomMemberDTO) {

        log.info("chatroomMember");
        // Validate user exists

        Boolean exists = userClient.userExistWithId(chatRoomMemberDTO.getUserId()).getBody().getData();

        if (!exists) {
            throw new ResourceNotFoundException("User not found with userId: " + chatRoomMemberDTO.getUserId());
        }

        // IMPROVED: Validate ChatRoom actually exists in database
        if (!chatRoomRepository.existsById(chatRoomMemberDTO.getChatRoomId())) {
            throw new ResourceNotFoundException("ChatRoom not found with id: " + chatRoomMemberDTO.getChatRoomId());
        }

        // Check for duplicate membership
        Optional<ChatRoomMember> existingMember = chatRoomMemberRepository
                .findByChatRoomIdAndUserId(chatRoomMemberDTO.getChatRoomId(), chatRoomMemberDTO.getUserId());

        if (existingMember.isPresent()) {
            throw new RuntimeException("User is already a member of this chat room");
        }

        ChatRoomMember chatRoomMember = mapperUtils.mapWithRelations(chatRoomMemberDTO, ChatRoomMember.class);
        chatRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        return mapperUtils.map(chatRoomMember, ChatRoomMemberDTO.class);
    }




    @Override
    public List<ChatRoomMemberDTO> getAllChatRoomMember() {
        return chatRoomMemberRepository.findAll()
                .stream()
                .map(element -> mapperUtils.map(element, ChatRoomMemberDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ChatRoomMemberDTO getChatRoomMemberById(String chatRoomMemberId) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findById(chatRoomMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoomMember Not found with this id: " + chatRoomMemberId));

        return mapperUtils.map(chatRoomMember, ChatRoomMemberDTO.class);
    }

    @Override
    public ChatRoomMemberDTO deleteChatRoomMemberById(String chatRoomMemberId) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findById(chatRoomMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoomMember Not found with this id: " + chatRoomMemberId));

        chatRoomMemberRepository.delete(chatRoomMember);

        return mapperUtils.map(chatRoomMember, ChatRoomMemberDTO.class);
    }

    @Override
    public ChatRoomMemberDTO updateChatRoomMemberById(String chatRoomMemberId, ChatRoomMemberDTO chatRoomMemberDTO) {
        ChatRoomMember chatRoomMember = chatRoomMemberRepository.findById(chatRoomMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("ChatRoomMember Not found with this id: " + chatRoomMemberId));

        // only update role
        chatRoomMember.setRole(chatRoomMemberDTO.getRole());

        chatRoomMember = chatRoomMemberRepository.save(chatRoomMember);

        return mapperUtils.map(chatRoomMember, ChatRoomMemberDTO.class);
    }

    @Override
    public List<ChatRoomMemberDTO> getAllChatRoomMemberByChatRoomId(String chatRoomId) {
        return chatRoomMemberRepository.getByChatRoomId(chatRoomId)
                .stream()
                .map(element -> mapperUtils.map(element, ChatRoomMemberDTO.class))
                .collect(Collectors.toList());
    }
}
