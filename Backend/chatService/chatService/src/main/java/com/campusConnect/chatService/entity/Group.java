package com.campusConnect.chatService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long createdBy;

//    @ElementCollection
//    private List<User> members;
    //TODO adding chats according to classroom or club-wise chats
}