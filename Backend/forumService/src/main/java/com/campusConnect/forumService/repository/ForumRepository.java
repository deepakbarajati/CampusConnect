package com.campusConnect.forumService.repository;

import com.campusConnect.forumService.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findByCreatedBy(Long createdBy);
}
