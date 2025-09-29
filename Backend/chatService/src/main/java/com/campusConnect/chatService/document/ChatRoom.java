package com.campusConnect.chatService.document;

import com.campusConnect.chatService.document.enums.ChatType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "chat_rooms")
public class ChatRoom {
  @Id
  private String id; // MongoDB's ObjectId, typically a string

  private String name;

  private ChatType type; // Enum stored as string

  private Long createdBy; // Reference to the creator's user ID (immutable in logic, not enforced by annotation)
}
