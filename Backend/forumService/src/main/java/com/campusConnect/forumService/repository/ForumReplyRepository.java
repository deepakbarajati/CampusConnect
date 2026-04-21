package com.campusConnect.forumService.repository;

import com.campusConnect.forumService.entity.ForumReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {
    List<ForumReply> findByForumId(Long forumId);
    List<ForumReply> findByUserId(Long userId);
}
