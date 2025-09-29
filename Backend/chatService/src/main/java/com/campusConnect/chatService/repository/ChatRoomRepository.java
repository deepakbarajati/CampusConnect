package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.document.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
}