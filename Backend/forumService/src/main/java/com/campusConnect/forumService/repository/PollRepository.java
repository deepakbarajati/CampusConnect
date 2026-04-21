package com.campusConnect.forumService.repository;

import com.campusConnect.forumService.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByForumId(Long forumId);
    List<Poll> findByCreatedBy(Long createdBy);
}
