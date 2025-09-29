package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.document.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {

    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    List<Message> findByChatRoomId(Long chatRoomId);
}
