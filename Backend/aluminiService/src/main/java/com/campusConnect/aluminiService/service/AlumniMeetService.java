package com.campusConnect.aluminiService.service;

import com.campusConnect.aluminiService.dto.AlumniMeetDTO;
import com.campusConnect.aluminiService.nodes.enums.Mode;

import java.time.LocalDateTime;
import java.util.List;

public interface AlumniMeetService {

    // CRUD Operations
    AlumniMeetDTO createAlumniMeet(AlumniMeetDTO alumniMeetDTO);
    AlumniMeetDTO getAlumniMeetById(Long id);
    List<AlumniMeetDTO> getAllAlumniMeets();
    AlumniMeetDTO updateAlumniMeet(Long id, AlumniMeetDTO alumniMeetDTO);
    void deleteAlumniMeet(Long id);

    // Query Operations
    List<AlumniMeetDTO> getMeetsByOrganizer(Long organizerId);
    List<AlumniMeetDTO> getMeetsByMode(Mode mode);
    List<AlumniMeetDTO> getMeetsByStatus(String status);
    List<AlumniMeetDTO> getMeetsByParticipant(Long participantId);
    List<AlumniMeetDTO> getUpcomingMeets();
    List<AlumniMeetDTO> getMeetsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    List<AlumniMeetDTO> searchMeetsByTitle(String title);
    List<AlumniMeetDTO> getMeetsByTag(String tag);

    // Participant Management
    AlumniMeetDTO addParticipant(Long meetId, Long participantId);
    AlumniMeetDTO removeParticipant(Long meetId, Long participantId);
    Integer getParticipantCount(Long meetId);

    // Status Management
    AlumniMeetDTO updateMeetStatus(Long meetId, String status);

    // Utility Methods
    boolean isMeetFull(Long meetId);
    boolean isUserParticipant(Long meetId, Long userId);
}
