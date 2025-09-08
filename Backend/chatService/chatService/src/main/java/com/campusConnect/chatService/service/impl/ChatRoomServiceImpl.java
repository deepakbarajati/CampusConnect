package com.campusConnect.chatService.service.impl;

import com.campusConnect.chatService.advice.ApiResponse;
import com.campusConnect.chatService.dto.ChatRoomDTO;
import com.campusConnect.chatService.entity.ChatRoom;
import com.campusConnect.chatService.exception.ResourceNotFoundException;
import com.campusConnect.chatService.repository.ChatRoomRepository;
import com.campusConnect.chatService.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    private final ChatRoomRepository chatRoomRepository;


    @Override
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO)
    {

        if(!userExist(chatRoomDTO.getCreatedBy())){
            throw new ResourceNotFoundException("user not exist with this UserId: "+chatRoomDTO.getCreatedBy());
        }

        ChatRoom chatRoom= modelMapper.map(chatRoomDTO, ChatRoom.class);

        chatRoom=chatRoomRepository.save(chatRoom);
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public List<ChatRoomDTO> getAllChatRoom() {
        List<ChatRoom> chatRooms=chatRoomRepository.findAll();
        return chatRooms
                .stream()
                .map((element) -> modelMapper.map(element, ChatRoomDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public ChatRoomDTO getChatRoomById(Long chatRoomId) {
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+chatRoomId));

        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO deleteChatRoomById(Long chatRoomId) {
//        profileRepository.deleteByUserId(userId); there is some fields need to be added
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+chatRoomId));
        chatRoomRepository.delete(chatRoom);
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }

    @Override
    public ChatRoomDTO updateChatRoomById(Long chatRoomId, ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom=chatRoomRepository.findById(chatRoomId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+chatRoomId));
        chatRoom.setName(chatRoomDTO.getName());
        chatRoom.setType(chatRoomDTO.getType());
        return modelMapper.map(chatRoom, ChatRoomDTO.class);
    }


    private Boolean userExist(Long userId){
        String uri="exists/"+userId;
        ApiResponse<Boolean> exist= restClient.get()
                .uri(uri)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return exist.getData();
    }
}
