package com.campusConnect.chatService.entity;


import com.campusConnect.chatService.entity.enums.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    private String fileUrl;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @CreationTimestamp
    private LocalDateTime uploadedAt;
}
