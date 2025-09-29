package com.campusConnect.aluminiService.service.impl;

import com.campusConnect.aluminiService.dto.AlumniMeetDTO;
import com.campusConnect.aluminiService.nodes.AlumniMeet;
import com.campusConnect.aluminiService.nodes.enums.Mode;
import com.campusConnect.aluminiService.repository.AlumniMeetRepository;
import com.campusConnect.aluminiService.service.AlumniMeetService;
import com.campusConnect.aluminiService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AlumniMeetServiceImpl implements AlumniMeetService {

    private final AlumniMeetRepository alumniMeetRepository;
    private final MapperUtils mapperUtils;

    @Override
    public AlumniMeetDTO createAlumniMeet(AlumniMeetDTO alumniMeetDTO) {
        log.info("Creating new alumni meet with title: {}", alumniMeetDTO.getTitle());

        AlumniMeet alumniMeet = mapperUtils.map(alumniMeetDTO, AlumniMeet.class);
        alumniMeet.setCreatedAt(LocalDateTime.now());
        alumniMeet.setUpdatedAt(LocalDateTime.now());

        // Set default values
        if (alumniMeet.getStatus() == null || alumniMeet.getStatus().isEmpty()) {
            alumniMeet.setStatus("SCHEDULED");
        }
        if (alumniMeet.getParticipantIds() == null) {
            alumniMeet.setParticipantIds(new ArrayList<>());
        }

        AlumniMeet savedMeet = alumniMeetRepository.save(alumniMeet);
        log.info("Alumni meet created successfully with ID: {}", savedMeet.getId());

        return mapperUtils.map(savedMeet, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumniMeetDTO getAlumniMeetById(Long id) {
        log.info("Fetching alumni meet with ID: {}", id);

        AlumniMeet alumniMeet = alumniMeetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + id));

        return mapperUtils.map(alumniMeet, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getAllAlumniMeets() {
        log.info("Fetching all alumni meets");

        List<AlumniMeet> meets = alumniMeetRepository.findAll();
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    public AlumniMeetDTO updateAlumniMeet(Long id, AlumniMeetDTO alumniMeetDTO) {
        log.info("Updating alumni meet with ID: {}", id);

        AlumniMeet existingMeet = alumniMeetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + id));

        // Update fields
        existingMeet.setTitle(alumniMeetDTO.getTitle());
        existingMeet.setMode(alumniMeetDTO.getMode());
        existingMeet.setDescription(alumniMeetDTO.getDescription());
        existingMeet.setLocation(alumniMeetDTO.getLocation());
        existingMeet.setMeetingLink(alumniMeetDTO.getMeetingLink());
        existingMeet.setScheduledDateTime(alumniMeetDTO.getScheduledDateTime());
        existingMeet.setDuration(alumniMeetDTO.getDuration());
        existingMeet.setMaxParticipants(alumniMeetDTO.getMaxParticipants());
        existingMeet.setStatus(alumniMeetDTO.getStatus());
        existingMeet.setTags(alumniMeetDTO.getTags());
        existingMeet.setUpdatedAt(LocalDateTime.now());

        AlumniMeet updatedMeet = alumniMeetRepository.save(existingMeet);
        log.info("Alumni meet updated successfully with ID: {}", updatedMeet.getId());

        return mapperUtils.map(updatedMeet, AlumniMeetDTO.class);
    }

    @Override
    public void deleteAlumniMeet(Long id) {
        log.info("Deleting alumni meet with ID: {}", id);

        if (!alumniMeetRepository.existsById(id)) {
            throw new RuntimeException("Alumni meet not found with ID: " + id);
        }

        alumniMeetRepository.deleteById(id);
        log.info("Alumni meet deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsByOrganizer(Long organizerId) {
        log.info("Fetching meets organized by user: {}", organizerId);

        List<AlumniMeet> meets = alumniMeetRepository.findByOrganizerId(organizerId);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsByMode(Mode mode) {
        log.info("Fetching meets with mode: {}", mode);

        List<AlumniMeet> meets = alumniMeetRepository.findByMode(mode);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsByStatus(String status) {
        log.info("Fetching meets with status: {}", status);

        List<AlumniMeet> meets = alumniMeetRepository.findByStatus(status);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsByParticipant(Long participantId) {
        log.info("Fetching meets for participant: {}", participantId);

        List<AlumniMeet> meets = alumniMeetRepository.findByParticipantId(participantId);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getUpcomingMeets() {
        log.info("Fetching upcoming meets");

        List<AlumniMeet> meets = alumniMeetRepository.findUpcomingMeets(LocalDateTime.now());
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching meets between {} and {}", startDate, endDate);

        List<AlumniMeet> meets = alumniMeetRepository.findMeetsBetweenDates(startDate, endDate);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> searchMeetsByTitle(String title) {
        log.info("Searching meets with title: {}", title);

        List<AlumniMeet> meets = alumniMeetRepository.findByTitleContainingIgnoreCase(title);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumniMeetDTO> getMeetsByTag(String tag) {
        log.info("Fetching meets with tag: {}", tag);

        List<AlumniMeet> meets = alumniMeetRepository.findByTag(tag);
        return mapperUtils.mapList(meets, AlumniMeetDTO.class);
    }

    @Override
    public AlumniMeetDTO addParticipant(Long meetId, Long participantId) {
        log.info("Adding participant {} to meet {}", participantId, meetId);

        AlumniMeet meet = alumniMeetRepository.findById(meetId)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + meetId));

        if (meet.getParticipantIds() == null) {
            meet.setParticipantIds(new ArrayList<>());
        }

        if (meet.getParticipantIds().contains(participantId)) {
            throw new RuntimeException("User is already a participant in this meet");
        }

        if (meet.getMaxParticipants() != null &&
                meet.getParticipantIds().size() >= meet.getMaxParticipants()) {
            throw new RuntimeException("Meet has reached maximum participant limit");
        }

        meet.getParticipantIds().add(participantId);
        meet.setUpdatedAt(LocalDateTime.now());

        AlumniMeet updatedMeet = alumniMeetRepository.save(meet);
        return mapperUtils.map(updatedMeet, AlumniMeetDTO.class);
    }

    @Override
    public AlumniMeetDTO removeParticipant(Long meetId, Long participantId) {
        log.info("Removing participant {} from meet {}", participantId, meetId);

        AlumniMeet meet = alumniMeetRepository.findById(meetId)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + meetId));

        if (meet.getParticipantIds() == null || !meet.getParticipantIds().contains(participantId)) {
            throw new RuntimeException("User is not a participant in this meet");
        }

        meet.getParticipantIds().remove(participantId);
        meet.setUpdatedAt(LocalDateTime.now());

        AlumniMeet updatedMeet = alumniMeetRepository.save(meet);
        return mapperUtils.map(updatedMeet, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getParticipantCount(Long meetId) {
        return alumniMeetRepository.countParticipants(meetId);
    }

    @Override
    public AlumniMeetDTO updateMeetStatus(Long meetId, String status) {
        log.info("Updating status of meet {} to {}", meetId, status);

        AlumniMeet meet = alumniMeetRepository.findById(meetId)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + meetId));

        meet.setStatus(status);
        meet.setUpdatedAt(LocalDateTime.now());

        AlumniMeet updatedMeet = alumniMeetRepository.save(meet);
        return mapperUtils.map(updatedMeet, AlumniMeetDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMeetFull(Long meetId) {
        AlumniMeet meet = alumniMeetRepository.findById(meetId)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + meetId));

        if (meet.getMaxParticipants() == null) {
            return false;
        }

        int currentParticipants = meet.getParticipantIds() != null ? meet.getParticipantIds().size() : 0;
        return currentParticipants >= meet.getMaxParticipants();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isUserParticipant(Long meetId, Long userId) {
        AlumniMeet meet = alumniMeetRepository.findById(meetId)
                .orElseThrow(() -> new RuntimeException("Alumni meet not found with ID: " + meetId));

        return meet.getParticipantIds() != null && meet.getParticipantIds().contains(userId);
    }
}
