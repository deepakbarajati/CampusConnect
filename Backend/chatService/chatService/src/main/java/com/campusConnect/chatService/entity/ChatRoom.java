package com.campusConnect.chatService.entity;

import com.campusConnect.chatService.entity.enums.ChatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "chat_room")
public class ChatRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Enumerated(EnumType.STRING)
  private ChatType type;

  private Long createdBy;
}