package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.client.UserClient;
import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import com.campusConnect.chatService.entity.ChatRoom;
import com.campusConnect.chatService.entity.ChatRoomMember;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.ChatRoomMemberRepository;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.service.ChatRoomMemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomMemberServiceImpl implements ChatRoomMemberService {
    private final ModelMapper modelMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserClient userClient;
    @Override
    public ChatRoomMemberDTO chatRoomMemberCreation(ChatRoomMemberDTO chatRoomMemberDTO) {
        //check user is exist with that userId provided
        if(!userClient.userExist(chatRoomMemberDTO.getUserId())){
            throw new ResourceNotFoundException("User Not found with userId: "+chatRoomMemberDTO.getUserId());
        }
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomMemberDTO.getChatRoomId()).orElseThrow(()->new ResourceNotFoundException("ChatRoom Not found"));
        ChatRoomMember chatRoomMember= ChatRoomMember
                .builder()
                .chatRoom(chatRoom)
                .role(chatRoomMemberDTO.getRole())
                .userId(chatRoomMemberDTO.getUserId())
                .build();
        chatRoomMember=chatRoomMemberRepository.save(chatRoomMember);
        chatRoomMemberDTO=modelMapper.map(chatRoomMember, ChatRoomMemberDTO.class);
        return chatRoomMemberDTO;
    }

    @Override
    public List<ChatRoomMemberDTO> getAllChatRoomMember() {
        List<ChatRoomMember> chatRoomMembers=chatRoomMemberRepository.findAll();

        return chatRoomMembers
                .stream()
                .map((element) -> modelMapper.map(element, ChatRoomMemberDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public ChatRoomMemberDTO getChatRoomMemberById(Long chatRoomMemberId) {
        ChatRoomMember chatRoomMember=chatRoomMemberRepository
                .findById(chatRoomMemberId)
                .orElseThrow(()->new ResourceNotFoundException("ChatRoomMember Not found with this id: "+chatRoomMemberId));
        return modelMapper.map(chatRoomMember, ChatRoomMemberDTO.class);
    }



    @Override
    public ChatRoomMemberDTO deleteChatRoomMemberById(Long chatRoomMemberId) {
        ChatRoomMember chatRoomMember=chatRoomMemberRepository
                .findById(chatRoomMemberId)
                .orElseThrow(()->new ResourceNotFoundException("ChatRoomMember Not found with this id: "+chatRoomMemberId));
        chatRoomMemberRepository.delete(chatRoomMember);
        return modelMapper.map(chatRoomMember,ChatRoomMemberDTO.class);
    }

    @Override
    public ChatRoomMemberDTO updateChatRoomMemberById(Long chatRoomMemberId, ChatRoomMemberDTO chatRoomMemberDTO) {
        ChatRoomMember chatRoomMember=chatRoomMemberRepository
                .findById(chatRoomMemberId)
                .orElseThrow(()->new ResourceNotFoundException("ChatRoomMember Not found with this id: "+chatRoomMemberId));
//        chatRoomMember= ChatRoomMember
//                .builder()
//                .role(chatRoomMemberDTO.getRole()) it gives error because it creaate an new enity
//                .build();
        chatRoomMember.setRole(chatRoomMemberDTO.getRole());
        chatRoomMember=chatRoomMemberRepository.save(chatRoomMember);
        return modelMapper.map(chatRoomMember, ChatRoomMemberDTO.class);
    }

    @Override
    public List<ChatRoomMemberDTO> getAllChatRoomMemberByChatRoomId(Long chatRoomId) {
        List<ChatRoomMember> chatRoomMembers=chatRoomMemberRepository.getByChatRoomId(chatRoomId);
        return chatRoomMembers
                .stream()
                .map((element) -> modelMapper.map(element, ChatRoomMemberDTO.class))
                .collect(Collectors.toList());
    }

}
