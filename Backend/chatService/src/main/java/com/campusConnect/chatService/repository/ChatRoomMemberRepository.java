package com.campusConnect.chatService.repository;

import com.campusConnect.chatService.document.ChatRoomMember;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends MongoRepository<ChatRoomMember, String> {


    List<ChatRoomMember> getByChatRoomId(String chatRoomId);

    void deleteByChatRoomId(String chatRoomId);

    Optional<ChatRoomMember> findByChatRoomIdAndUserId(String chatRoomId, Long userId);
}