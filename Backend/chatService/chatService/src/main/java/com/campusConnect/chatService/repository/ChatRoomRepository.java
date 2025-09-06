package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}