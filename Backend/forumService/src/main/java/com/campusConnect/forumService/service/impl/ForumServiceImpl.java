package com.campusConnect.forumService.service.impl;

import com.campusConnect.forumService.dto.*;
import com.campusConnect.forumService.entity.*;
import com.campusConnect.forumService.repository.*;
import com.campusConnect.forumService.service.ForumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final ForumReplyRepository replyRepository;
    private final CategoryRepository categoryRepository;
    private final PollRepository pollRepository;
    private final PollVoteRepository pollVoteRepository;
    private final ClubRepository clubRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    @Override
    public ForumDTO createForum(ForumDTO forumDTO) {
        log.info("Creating forum: {}", forumDTO.getTitle());
        Forum forum = modelMapper.map(forumDTO, Forum.class);
        Forum saved = forumRepository.save(forum);
        return modelMapper.map(saved, ForumDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ForumDTO getForumById(Long id) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        return modelMapper.map(forum, ForumDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForumDTO> getAllForums() {
        return forumRepository.findAll().stream()
                .map(f -> modelMapper.map(f, ForumDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ForumDTO updateForum(Long id, ForumDTO forumDTO) {
        Forum forum = forumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        forum.setTitle(forumDTO.getTitle());
        forum.setDescription(forumDTO.getDescription());
        Forum saved = forumRepository.save(forum);
        return modelMapper.map(saved, ForumDTO.class);
    }

    @Override
    public void deleteForum(Long id) {
        forumRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForumDTO> getForumsByUser(Long userId) {
        return forumRepository.findByCreatedBy(userId).stream()
                .map(f -> modelMapper.map(f, ForumDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ForumReplyDTO createReply(ForumReplyDTO replyDTO) {
        Forum forum = forumRepository.findById(replyDTO.getForumId())
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        ForumReply reply = modelMapper.map(replyDTO, ForumReply.class);
        reply.setForum(forum);
        reply.setTimestamp(LocalDateTime.now());
        ForumReply saved = replyRepository.save(reply);
        return modelMapper.map(saved, ForumReplyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForumReplyDTO> getRepliesByForum(Long forumId) {
        return replyRepository.findByForumId(forumId).stream()
                .map(r -> modelMapper.map(r, ForumReplyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ForumReplyDTO> getRepliesByUser(Long userId) {
        return replyRepository.findByUserId(userId).stream()
                .map(r -> modelMapper.map(r, ForumReplyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReply(Long id) {
        replyRepository.deleteById(id);
    }

    // Category operations
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        log.info("Creating category: {}", categoryDTO.getName());
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    // Poll operations
    @Override
    public PollDTO createPoll(PollDTO pollDTO) {
        log.info("Creating poll: {}", pollDTO.getQuestion());
        Forum forum = forumRepository.findById(pollDTO.getForumId())
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        Poll poll = modelMapper.map(pollDTO, Poll.class);
        poll.setForum(forum);
        Poll saved = pollRepository.save(poll);
        return modelMapper.map(saved, PollDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PollDTO getPollById(Long id) {
        Poll poll = pollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        return modelMapper.map(poll, PollDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollDTO> getPollsByForum(Long forumId) {
        return pollRepository.findByForumId(forumId).stream()
                .map(p -> modelMapper.map(p, PollDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PollVoteDTO voteOnPoll(PollVoteDTO voteDTO) {
        Poll poll = pollRepository.findById(voteDTO.getPollId())
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        // Check if user already voted
        pollVoteRepository.findByPollIdAndUserId(voteDTO.getPollId(), voteDTO.getUserId())
                .ifPresent(v -> { throw new RuntimeException("User already voted"); });
        PollVote vote = modelMapper.map(voteDTO, PollVote.class);
        vote.setPoll(poll);
        PollVote saved = pollVoteRepository.save(vote);
        return modelMapper.map(saved, PollVoteDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PollVoteDTO> getVotesByPoll(Long pollId) {
        return pollVoteRepository.findByPollId(pollId).stream()
                .map(v -> modelMapper.map(v, PollVoteDTO.class))
                .collect(Collectors.toList());
    }

    // Club operations
    @Override
    public ClubDTO createClub(ClubDTO clubDTO) {
        log.info("Creating club: {}", clubDTO.getName());
        Club club = modelMapper.map(clubDTO, Club.class);
        Club saved = clubRepository.save(club);
        return modelMapper.map(saved, ClubDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ClubDTO getClubById(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        return modelMapper.map(club, ClubDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO> getAllClubs() {
        return clubRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ClubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClubDTO updateClub(Long id, ClubDTO clubDTO) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        club.setName(clubDTO.getName());
        club.setDescription(clubDTO.getDescription());
        Club saved = clubRepository.save(club);
        return modelMapper.map(saved, ClubDTO.class);
    }

    @Override
    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClubDTO> getClubsByUser(Long userId) {
        return clubRepository.findByCreatedBy(userId).stream()
                .map(c -> modelMapper.map(c, ClubDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClubDTO addMemberToClub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        if (!club.getMembers().contains(userId)) {
            club.getMembers().add(userId);
            clubRepository.save(club);
        }
        return modelMapper.map(club, ClubDTO.class);
    }

    @Override
    public ClubDTO removeMemberFromClub(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found"));
        club.getMembers().remove(userId);
        clubRepository.save(club);
        return modelMapper.map(club, ClubDTO.class);
    }

    // Event operations
    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        log.info("Creating event: {}", eventDTO.getTitle());
        Club club = clubRepository.findById(eventDTO.getClubId())
                .orElseThrow(() -> new RuntimeException("Club not found"));
        Event event = modelMapper.map(eventDTO, Event.class);
        event.setClub(club);
        Event saved = eventRepository.save(event);
        return modelMapper.map(saved, EventDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return modelMapper.map(event, EventDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getEventsByClub(Long clubId) {
        return eventRepository.findByClubId(clubId).stream()
                .map(e -> modelMapper.map(e, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(e -> modelMapper.map(e, EventDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate());
        event.setVenue(eventDTO.getVenue());
        Event saved = eventRepository.save(event);
        return modelMapper.map(saved, EventDTO.class);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
