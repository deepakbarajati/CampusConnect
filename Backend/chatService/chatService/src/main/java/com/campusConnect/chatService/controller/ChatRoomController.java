package com.campusConnect.chatService.controller;

import com.campusConnect.chatService.dto.ChatRoomDTO;
import com.campusConnect.chatService.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping()
    public ResponseEntity<ChatRoomDTO> createChatRoom(@Valid @RequestBody ChatRoomDTO chatRoomDTO){
        return new ResponseEntity<>(chatRoomService.createChatRoom(chatRoomDTO), HttpStatus.CREATED);
    }
}
