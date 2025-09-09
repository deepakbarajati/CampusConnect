package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {


    List<ChatRoomMember> getByChatRoomId(Long chatRoomId);

    void deleteByChatRoomId(Long chatRoomId);
}