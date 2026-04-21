package com.campusConnect.forumService.repository;

import com.campusConnect.forumService.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByCreatedBy(Long createdBy);
}
