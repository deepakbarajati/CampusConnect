package com.campusConnect.aluminiService.service.impl;

import com.campusConnect.aluminiService.dto.MentorshipDTO;
import com.campusConnect.aluminiService.nodes.Mentorship;
import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import com.campusConnect.aluminiService.repository.MentorshipRepository;
import com.campusConnect.aluminiService.service.MentorshipService;
import com.campusConnect.aluminiService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MentorshipServiceImpl implements MentorshipService {

    private final MentorshipRepository mentorshipRepository;
    private final MapperUtils mapperUtils;

    @Override
    public MentorshipDTO createMentorship(MentorshipDTO mentorshipDTO) {
        log.info("Creating new mentorship between mentor {} and mentee {}",
                mentorshipDTO.getMentorId(), mentorshipDTO.getMenteeId());

        Mentorship mentorship = mapperUtils.map(mentorshipDTO, Mentorship.class);
        mentorship.setCreatedAt(new Date());
        mentorship.setUpdatedAt(new Date());

        // Set default values
        if (mentorship.getStatus() == null) {
            mentorship.setStatus(MentorShipStatus.REQUESTED);
        }
        if (mentorship.getSessionCount() == null) {
            mentorship.setSessionCount(0);
        }

        Mentorship savedMentorship = mentorshipRepository.save(mentorship);
        log.info("Mentorship created successfully with ID: {}", savedMentorship.getId());

        return mapperUtils.map(savedMentorship, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public MentorshipDTO getMentorshipById(Long id) {
        log.info("Fetching mentorship with ID: {}", id);

        Mentorship mentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        return mapperUtils.map(mentorship, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getAllMentorships() {
        log.info("Fetching all mentorships");

        List<Mentorship> mentorships = mentorshipRepository.findAll();
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    public MentorshipDTO updateMentorship(Long id, MentorshipDTO mentorshipDTO) {
        log.info("Updating mentorship with ID: {}", id);

        Mentorship existingMentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        // Update fields
        existingMentorship.setTitle(mentorshipDTO.getTitle());
        existingMentorship.setDescription(mentorshipDTO.getDescription());
        existingMentorship.setStartDate(mentorshipDTO.getStartDate());
        existingMentorship.setEndDate(mentorshipDTO.getEndDate());
        existingMentorship.setStatus(mentorshipDTO.getStatus());
        existingMentorship.setGoals(mentorshipDTO.getGoals());
        existingMentorship.setMeetingFrequency(mentorshipDTO.getMeetingFrequency());
        existingMentorship.setMeetingMode(mentorshipDTO.getMeetingMode());
        existingMentorship.setFeedbackRating(mentorshipDTO.getFeedbackRating());
        existingMentorship.setUpdatedAt(new Date());

        Mentorship updatedMentorship = mentorshipRepository.save(existingMentorship);
        log.info("Mentorship updated successfully with ID: {}", updatedMentorship.getId());

        return mapperUtils.map(updatedMentorship, MentorshipDTO.class);
    }

    @Override
    public void deleteMentorship(Long id) {
        log.info("Deleting mentorship with ID: {}", id);

        if (!mentorshipRepository.existsById(id)) {
            throw new RuntimeException("Mentorship not found with ID: " + id);
        }

        mentorshipRepository.deleteById(id);
        log.info("Mentorship deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByMentor(Long mentorId) {
        log.info("Fetching mentorships for mentor: {}", mentorId);

        List<Mentorship> mentorships = mentorshipRepository.findByMentorId(mentorId);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByMentee(Long menteeId) {
        log.info("Fetching mentorships for mentee: {}", menteeId);

        List<Mentorship> mentorships = mentorshipRepository.findByMenteeId(menteeId);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByStatus(MentorShipStatus status) {
        log.info("Fetching mentorships with status: {}", status);

        List<Mentorship> mentorships = mentorshipRepository.findByStatus(status);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByMeetingFrequency(String meetingFrequency) {
        log.info("Fetching mentorships with meeting frequency: {}", meetingFrequency);

        List<Mentorship> mentorships = mentorshipRepository.findByMeetingFrequency(meetingFrequency);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByMeetingMode(String meetingMode) {
        log.info("Fetching mentorships with meeting mode: {}", meetingMode);

        List<Mentorship> mentorships = mentorshipRepository.findByMeetingMode(meetingMode);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsBetweenUsers(Long mentorId, Long menteeId) {
        log.info("Fetching mentorships between mentor {} and mentee {}", mentorId, menteeId);

        List<Mentorship> mentorships = mentorshipRepository.findByMentorIdAndMenteeId(mentorId, menteeId);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getActiveMentorshipsByMentor(Long mentorId) {
        log.info("Fetching active mentorships for mentor: {}", mentorId);

        List<Mentorship> mentorships = mentorshipRepository.findActiveMentorshipsByMentor(mentorId);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getActiveMentorshipsByMentee(Long menteeId) {
        log.info("Fetching active mentorships for mentee: {}", menteeId);

        List<Mentorship> mentorships = mentorshipRepository.findActiveMentorshipsByMentee(menteeId);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsEndingSoon(int days) {
        log.info("Fetching mentorships ending within {} days", days);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, days);
        Date endDate = cal.getTime();

        List<Mentorship> mentorships = mentorshipRepository.findMentorshipsEndingSoon(endDate);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsByDateRange(Date startDate, Date endDate) {
        log.info("Fetching mentorships between {} and {}", startDate, endDate);

        List<Mentorship> mentorships = mentorshipRepository.findMentorshipsByDateRange(startDate, endDate);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> getMentorshipsWithHighRatings(Double minRating) {
        log.info("Fetching mentorships with rating >= {}", minRating);

        List<Mentorship> mentorships = mentorshipRepository.findMentorshipsWithHighRatings(minRating);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorshipDTO> searchMentorshipsByTitle(String title) {
        log.info("Searching mentorships with title: {}", title);

        List<Mentorship> mentorships = mentorshipRepository.findByTitleContainingIgnoreCase(title);
        return mapperUtils.mapList(mentorships, MentorshipDTO.class);
    }

    @Override
    public MentorshipDTO updateMentorshipStatus(Long id, MentorShipStatus status) {
        log.info("Updating status of mentorship {} to {}", id, status);

        Mentorship mentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        mentorship.setStatus(status);
        mentorship.setUpdatedAt(new Date());

        Mentorship updatedMentorship = mentorshipRepository.save(mentorship);
        return mapperUtils.map(updatedMentorship, MentorshipDTO.class);
    }

    @Override
    public MentorshipDTO acceptMentorshipRequest(Long id) {
        log.info("Accepting mentorship request: {}", id);
        return updateMentorshipStatus(id, MentorShipStatus.ACCEPTED);
    }

    @Override
    public MentorshipDTO completeMentorship(Long id) {
        log.info("Completing mentorship: {}", id);

        Mentorship mentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        mentorship.setStatus(MentorShipStatus.COMPLETED);
        mentorship.setEndDate(new Date());
        mentorship.setUpdatedAt(new Date());

        Mentorship updatedMentorship = mentorshipRepository.save(mentorship);
        return mapperUtils.map(updatedMentorship, MentorshipDTO.class);
    }

    @Override
    public MentorshipDTO pauseMentorship(Long id) {
        log.info("Pausing mentorship: {}", id);
        return updateMentorshipStatus(id, MentorShipStatus.PAUSED);
    }

    @Override
    public MentorshipDTO resumeMentorship(Long id) {
        log.info("Resuming mentorship: {}", id);
        return updateMentorshipStatus(id, MentorShipStatus.ACTIVE);
    }

    @Override
    public MentorshipDTO addFeedbackRating(Long id, Double rating) {
        log.info("Adding feedback rating {} to mentorship: {}", rating, id);

        Mentorship mentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        mentorship.setFeedbackRating(rating);
        mentorship.setUpdatedAt(new Date());

        Mentorship updatedMentorship = mentorshipRepository.save(mentorship);
        return mapperUtils.map(updatedMentorship, MentorshipDTO.class);
    }

    @Override
    public MentorshipDTO incrementSessionCount(Long id) {
        log.info("Incrementing session count for mentorship: {}", id);

        Mentorship mentorship = mentorshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentorship not found with ID: " + id));

        int currentCount = mentorship.getSessionCount() != null ? mentorship.getSessionCount() : 0;
        mentorship.setSessionCount(currentCount + 1);
        mentorship.setUpdatedAt(new Date());

        Mentorship updatedMentorship = mentorshipRepository.save(mentorship);
        return mapperUtils.map(updatedMentorship, MentorshipDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countMentorshipsByMentor(Long mentorId) {
        return mentorshipRepository.countMentorshipsByMentor(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countMentorshipsByMentee(Long menteeId) {
        return mentorshipRepository.countMentorshipsByMentee(menteeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countMentorshipsByStatus(MentorShipStatus status) {
        return mentorshipRepository.countMentorshipsByStatus(status.name());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMentorshipActive(Long mentorId, Long menteeId) {
        List<Mentorship> mentorships = mentorshipRepository.findByMentorIdAndMenteeId(mentorId, menteeId);
        return mentorships.stream()
                .anyMatch(m -> m.getStatus() == MentorShipStatus.ACTIVE);
    }

    @Override
    public void expireMentorships() {
        log.info("Expiring mentorships that have passed their end date");

        Date now = new Date();
        List<Mentorship> activeMentorships = mentorshipRepository.findByStatus(MentorShipStatus.ACTIVE);

        activeMentorships.stream()
                .filter(m -> m.getEndDate() != null && m.getEndDate().before(now))
                .forEach(m -> {
                    m.setStatus(MentorShipStatus.COMPLETED);
                    m.setUpdatedAt(new Date());
                    mentorshipRepository.save(m);
                });

        log.info("Expired mentorships have been completed");
    }
}
