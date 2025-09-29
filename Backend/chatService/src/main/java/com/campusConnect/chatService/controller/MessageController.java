package com.campusConnect.chatService.controller;

import com.campusConnect.chatService.dto.MessageDTO;
import com.campusConnect.chatService.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@Valid @RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.createMessage(messageDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable String id, @Valid @RequestBody MessageDTO messageDTO) {
        return ResponseEntity.ok(messageService.updateMessage(id, messageDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable String id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(messageService.getAllMessagesByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable String id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<MessageDTO> markAsRead(@PathVariable String id) {
        return ResponseEntity.ok(messageService.markMessageAsRead(id));
    }

    @PatchMapping("/chat/{chatRoomId}/user/{userId}/read")
    public ResponseEntity<List<MessageDTO>> markAllAsRead(
            @PathVariable String chatRoomId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(messageService.markAllMessagesAsRead(userId, chatRoomId));
    }

}
