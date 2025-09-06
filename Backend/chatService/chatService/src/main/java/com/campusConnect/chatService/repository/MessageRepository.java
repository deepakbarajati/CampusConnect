package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}