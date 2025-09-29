package com.campusConnect.chatService.controller;

import com.campusConnect.chatService.dto.ChatRoomDTO;
import com.campusConnect.chatService.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatRoom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping()
    public ResponseEntity<ChatRoomDTO> createChatRoom(@Valid @RequestBody ChatRoomDTO chatRoomDTO){
        return new ResponseEntity<>(chatRoomService.createChatRoom(chatRoomDTO), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ChatRoomDTO>> getAllChatRoom(){
        return new ResponseEntity<>(chatRoomService.getAllChatRoom(),HttpStatus.OK);
    }


    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDTO> getChatRoomById(@PathVariable String chatRoomId){
        return new ResponseEntity<>(chatRoomService.getChatRoomById(chatRoomId),HttpStatus.FOUND);
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDTO> deleteChatRoomById(@PathVariable String chatRoomId){
        return new ResponseEntity<>(chatRoomService.deleteChatRoomById(chatRoomId),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDTO> updateChatRoomById(@PathVariable String chatRoomId, @RequestBody ChatRoomDTO chatRoomDTO){
        return new ResponseEntity<>(chatRoomService.updateChatRoomById(chatRoomId,chatRoomDTO),HttpStatus.ACCEPTED);
    }
}
