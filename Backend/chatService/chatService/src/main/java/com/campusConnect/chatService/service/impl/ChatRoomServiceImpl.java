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

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ModelMapper modelMapper;
    private final RestClient restClient;
    private final ChatRoomRepository chatRoomRepository;


    @Override
    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO) {

        if(!userExist(chatRoomDTO.getCreatedBy())){
            throw new ResourceNotFoundException("user not exist with this UserId: "+chatRoomDTO.getCreatedBy());
        }

        ChatRoom chatRoom= modelMapper.map(chatRoomDTO, ChatRoom.class);

        chatRoom=chatRoomRepository.save(chatRoom);
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
