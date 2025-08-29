package com.campusConnect.forumService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class ForumReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id")
    private Forum forum;

    private Long userId;

    private String message;

    private LocalDateTime timestamp;
}