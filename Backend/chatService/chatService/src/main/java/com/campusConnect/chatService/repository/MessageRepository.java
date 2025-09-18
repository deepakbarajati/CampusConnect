package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    List<Message> findByChatRoomId(Long chatRoomId);
}
