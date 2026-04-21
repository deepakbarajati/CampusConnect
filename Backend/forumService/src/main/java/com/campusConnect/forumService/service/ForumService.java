package com.campusConnect.forumService.service;

import com.campusConnect.forumService.dto.*;

import java.util.List;

public interface ForumService {
    // Forum operations
    ForumDTO createForum(ForumDTO forumDTO);
    ForumDTO getForumById(Long id);
    List<ForumDTO> getAllForums();
    ForumDTO updateForum(Long id, ForumDTO forumDTO);
    void deleteForum(Long id);
    List<ForumDTO> getForumsByUser(Long userId);

    // ForumReply operations
    ForumReplyDTO createReply(ForumReplyDTO replyDTO);
    List<ForumReplyDTO> getRepliesByForum(Long forumId);
    List<ForumReplyDTO> getRepliesByUser(Long userId);
    void deleteReply(Long id);

    // Category operations
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();

    // Poll operations
    PollDTO createPoll(PollDTO pollDTO);
    PollDTO getPollById(Long id);
    List<PollDTO> getPollsByForum(Long forumId);
    PollVoteDTO voteOnPoll(PollVoteDTO voteDTO);
    List<PollVoteDTO> getVotesByPoll(Long pollId);

    // Club operations
    ClubDTO createClub(ClubDTO clubDTO);
    ClubDTO getClubById(Long id);
    List<ClubDTO> getAllClubs();
    ClubDTO updateClub(Long id, ClubDTO clubDTO);
    void deleteClub(Long id);
    List<ClubDTO> getClubsByUser(Long userId);
    ClubDTO addMemberToClub(Long clubId, Long userId);
    ClubDTO removeMemberFromClub(Long clubId, Long userId);

    // Event operations
    EventDTO createEvent(EventDTO eventDTO);
    EventDTO getEventById(Long id);
    List<EventDTO> getEventsByClub(Long clubId);
    List<EventDTO> getAllEvents();
    EventDTO updateEvent(Long id, EventDTO eventDTO);
    void deleteEvent(Long id);
}
