package com.campusConnect.aluminiService.service;

import com.campusConnect.aluminiService.dto.MentorshipDTO;
import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;

import java.util.Date;
import java.util.List;

public interface MentorshipService {

    // CRUD Operations
    MentorshipDTO createMentorship(MentorshipDTO mentorshipDTO);
    MentorshipDTO getMentorshipById(Long id);
    List<MentorshipDTO> getAllMentorships();
    MentorshipDTO updateMentorship(Long id, MentorshipDTO mentorshipDTO);
    void deleteMentorship(Long id);

    // Query Operations
    List<MentorshipDTO> getMentorshipsByMentor(Long mentorId);
    List<MentorshipDTO> getMentorshipsByMentee(Long menteeId);
    List<MentorshipDTO> getMentorshipsByStatus(MentorShipStatus status);
    List<MentorshipDTO> getMentorshipsByMeetingFrequency(String meetingFrequency);
    List<MentorshipDTO> getMentorshipsByMeetingMode(String meetingMode);
    List<MentorshipDTO> getMentorshipsBetweenUsers(Long mentorId, Long menteeId);
    List<MentorshipDTO> getActiveMentorshipsByMentor(Long mentorId);
    List<MentorshipDTO> getActiveMentorshipsByMentee(Long menteeId);
    List<MentorshipDTO> getMentorshipsEndingSoon(int days);
    List<MentorshipDTO> getMentorshipsByDateRange(Date startDate, Date endDate);
    List<MentorshipDTO> getMentorshipsWithHighRatings(Double minRating);
    List<MentorshipDTO> searchMentorshipsByTitle(String title);

    // Status Management
    MentorshipDTO updateMentorshipStatus(Long id, MentorShipStatus status);
    MentorshipDTO acceptMentorshipRequest(Long id);
    MentorshipDTO completeMentorship(Long id);
    MentorshipDTO pauseMentorship(Long id);
    MentorshipDTO resumeMentorship(Long id);

    // Feedback Management
    MentorshipDTO addFeedbackRating(Long id, Double rating);
    MentorshipDTO incrementSessionCount(Long id);

    // Statistics
    Long countMentorshipsByMentor(Long mentorId);
    Long countMentorshipsByMentee(Long menteeId);
    Long countMentorshipsByStatus(MentorShipStatus status);

    // Utility Methods
    boolean isMentorshipActive(Long mentorId, Long menteeId);
    void expireMentorships();
}
