package com.campusConnect.forumService.controller;

import com.campusConnect.forumService.dto.*;
import com.campusConnect.forumService.service.ForumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forums")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ForumController {

    private final ForumService forumService;

    // Forum endpoints
    @PostMapping
    public ResponseEntity<ForumDTO> createForum(@Valid @RequestBody ForumDTO forumDTO) {
        log.info("Creating forum: {}", forumDTO.getTitle());
        ForumDTO created = forumService.createForum(forumDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ForumDTO>> getAllForums() {
        List<ForumDTO> forums = forumService.getAllForums();
        return ResponseEntity.ok(forums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ForumDTO> getForumById(@PathVariable Long id) {
        ForumDTO forum = forumService.getForumById(id);
        return ResponseEntity.ok(forum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ForumDTO> updateForum(@PathVariable Long id, @Valid @RequestBody ForumDTO forumDTO) {
        ForumDTO updated = forumService.updateForum(id, forumDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ForumDTO>> getForumsByUser(@PathVariable Long userId) {
        List<ForumDTO> forums = forumService.getForumsByUser(userId);
        return ResponseEntity.ok(forums);
    }

    // Reply endpoints
    @PostMapping("/{forumId}/replies")
    public ResponseEntity<ForumReplyDTO> createReply(@PathVariable Long forumId, @Valid @RequestBody ForumReplyDTO replyDTO) {
        replyDTO.setForumId(forumId);
        ForumReplyDTO created = forumService.createReply(replyDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{forumId}/replies")
    public ResponseEntity<List<ForumReplyDTO>> getRepliesByForum(@PathVariable Long forumId) {
        List<ForumReplyDTO> replies = forumService.getRepliesByForum(forumId);
        return ResponseEntity.ok(replies);
    }

    @GetMapping("/replies/user/{userId}")
    public ResponseEntity<List<ForumReplyDTO>> getRepliesByUser(@PathVariable Long userId) {
        List<ForumReplyDTO> replies = forumService.getRepliesByUser(userId);
        return ResponseEntity.ok(replies);
    }

    @DeleteMapping("/replies/{id}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long id) {
        forumService.deleteReply(id);
        return ResponseEntity.noContent().build();
    }

    // Category endpoints
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = forumService.createCategory(categoryDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = forumService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Poll endpoints
    @PostMapping("/{forumId}/polls")
    public ResponseEntity<PollDTO> createPoll(@PathVariable Long forumId, @Valid @RequestBody PollDTO pollDTO) {
        pollDTO.setForumId(forumId);
        PollDTO created = forumService.createPoll(pollDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<PollDTO> getPollById(@PathVariable Long id) {
        PollDTO poll = forumService.getPollById(id);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/{forumId}/polls")
    public ResponseEntity<List<PollDTO>> getPollsByForum(@PathVariable Long forumId) {
        List<PollDTO> polls = forumService.getPollsByForum(forumId);
        return ResponseEntity.ok(polls);
    }

    @PostMapping("/polls/{pollId}/vote")
    public ResponseEntity<PollVoteDTO> voteOnPoll(@PathVariable Long pollId, @Valid @RequestBody PollVoteDTO voteDTO) {
        voteDTO.setPollId(pollId);
        PollVoteDTO created = forumService.voteOnPoll(voteDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}/votes")
    public ResponseEntity<List<PollVoteDTO>> getVotesByPoll(@PathVariable Long pollId) {
        List<PollVoteDTO> votes = forumService.getVotesByPoll(pollId);
        return ResponseEntity.ok(votes);
    }

    // Club endpoints
    @PostMapping("/clubs")
    public ResponseEntity<ClubDTO> createClub(@Valid @RequestBody ClubDTO clubDTO) {
        ClubDTO created = forumService.createClub(clubDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/clubs")
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        List<ClubDTO> clubs = forumService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("/clubs/{id}")
    public ResponseEntity<ClubDTO> getClubById(@PathVariable Long id) {
        ClubDTO club = forumService.getClubById(id);
        return ResponseEntity.ok(club);
    }

    @PutMapping("/clubs/{id}")
    public ResponseEntity<ClubDTO> updateClub(@PathVariable Long id, @Valid @RequestBody ClubDTO clubDTO) {
        ClubDTO updated = forumService.updateClub(id, clubDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/clubs/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        forumService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clubs/user/{userId}")
    public ResponseEntity<List<ClubDTO>> getClubsByUser(@PathVariable Long userId) {
        List<ClubDTO> clubs = forumService.getClubsByUser(userId);
        return ResponseEntity.ok(clubs);
    }

    @PostMapping("/clubs/{clubId}/members/{userId}")
    public ResponseEntity<ClubDTO> addMemberToClub(@PathVariable Long clubId, @PathVariable Long userId) {
        ClubDTO updated = forumService.addMemberToClub(clubId, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/clubs/{clubId}/members/{userId}")
    public ResponseEntity<ClubDTO> removeMemberFromClub(@PathVariable Long clubId, @PathVariable Long userId) {
        ClubDTO updated = forumService.removeMemberFromClub(clubId, userId);
        return ResponseEntity.ok(updated);
    }

    // Event endpoints
    @PostMapping("/clubs/{clubId}/events")
    public ResponseEntity<EventDTO> createEvent(@PathVariable Long clubId, @Valid @RequestBody EventDTO eventDTO) {
        eventDTO.setClubId(clubId);
        EventDTO created = forumService.createEvent(eventDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = forumService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        EventDTO event = forumService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/clubs/{clubId}/events")
    public ResponseEntity<List<EventDTO>> getEventsByClub(@PathVariable Long clubId) {
        List<EventDTO> events = forumService.getEventsByClub(clubId);
        return ResponseEntity.ok(events);
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        EventDTO updated = forumService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        forumService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
