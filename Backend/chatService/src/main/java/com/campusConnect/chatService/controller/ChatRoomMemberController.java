package com.campusConnect.chatService.controller;

import com.campusConnect.chatService.dto.ChatRoomMemberDTO;
import com.campusConnect.chatService.service.ChatRoomMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("chatRoomMember")
public class ChatRoomMemberController {

    private final ChatRoomMemberService chatRoomMemberService;

    @PostMapping()
    public ResponseEntity<ChatRoomMemberDTO> chatRoomMemberCreation(@Valid @RequestBody ChatRoomMemberDTO chatRoomMemberDTO) {
        return new ResponseEntity<>(chatRoomMemberService.chatRoomMemberCreation(chatRoomMemberDTO), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<ChatRoomMemberDTO>> getAllChatRoomMember(){
        return new ResponseEntity<>(chatRoomMemberService.getAllChatRoomMember(),HttpStatus.OK);
    }


    @GetMapping("/{chatRoomMemberId}")
    public ResponseEntity<ChatRoomMemberDTO> getChatRoomMemberById(@PathVariable String chatRoomMemberId){
        return new ResponseEntity<>(chatRoomMemberService.getChatRoomMemberById(chatRoomMemberId),HttpStatus.FOUND);
    }

    @DeleteMapping("/{chatRoomMemberId}")
    public ResponseEntity<ChatRoomMemberDTO> deleteChatRoomMemberById(@PathVariable String chatRoomMemberId){
        return new ResponseEntity<>(chatRoomMemberService.deleteChatRoomMemberById(chatRoomMemberId),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{chatRoomMemberId}")
    public ResponseEntity<ChatRoomMemberDTO> updateChatRoomMemberById(@PathVariable String chatRoomMemberId, @Valid @RequestBody ChatRoomMemberDTO chatRoomMemberDTO ){
        return new ResponseEntity<>(chatRoomMemberService.updateChatRoomMemberById(chatRoomMemberId,chatRoomMemberDTO),HttpStatus.ACCEPTED);
    }

    @GetMapping("/chatRoom/{chatRoomId}")
    public ResponseEntity<List<ChatRoomMemberDTO>> getAllChatRoomMemberByChatRoomId(@PathVariable String chatRoomId){
        return new ResponseEntity<>(chatRoomMemberService.getAllChatRoomMemberByChatRoomId(chatRoomId),HttpStatus.ACCEPTED);
    }
}
