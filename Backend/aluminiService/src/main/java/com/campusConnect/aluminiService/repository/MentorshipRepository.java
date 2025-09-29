package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Mentorship;
import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MentorshipRepository extends Neo4jRepository<Mentorship, Long> {

    // Find mentorships by mentor
    List<Mentorship> findByMentorId(Long mentorId);

    // Find mentorships by mentee
    List<Mentorship> findByMenteeId(Long menteeId);

    // Find mentorships by status
    List<Mentorship> findByStatus(MentorShipStatus status);

    // Find mentorships by meeting frequency
    List<Mentorship> findByMeetingFrequency(String meetingFrequency);

    // Find mentorships by meeting mode
    List<Mentorship> findByMeetingMode(String meetingMode);

    // Find mentorships between specific mentor and mentee
    @Query("MATCH (m:Mentorship) WHERE m.mentorId = $mentorId AND m.menteeId = $menteeId RETURN m")
    List<Mentorship> findByMentorIdAndMenteeId(@Param("mentorId") Long mentorId,
                                               @Param("menteeId") Long menteeId);

    // Find active mentorships for mentor
    @Query("MATCH (m:Mentorship) WHERE m.mentorId = $mentorId AND m.status = 'ACTIVE' RETURN m")
    List<Mentorship> findActiveMentorshipsByMentor(@Param("mentorId") Long mentorId);

    // Find active mentorships for mentee
    @Query("MATCH (m:Mentorship) WHERE m.menteeId = $menteeId AND m.status = 'ACTIVE' RETURN m")
    List<Mentorship> findActiveMentorshipsByMentee(@Param("menteeId") Long menteeId);

    // Find mentorships ending soon
    @Query("MATCH (m:Mentorship) WHERE m.endDate <= $endDate AND m.status = 'ACTIVE' RETURN m")
    List<Mentorship> findMentorshipsEndingSoon(@Param("endDate") Date endDate);

    // Find mentorships by date range
    @Query("MATCH (m:Mentorship) WHERE m.startDate >= $startDate AND m.startDate <= $endDate RETURN m")
    List<Mentorship> findMentorshipsByDateRange(@Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    // Find mentorships with high ratings
    @Query("MATCH (m:Mentorship) WHERE m.feedbackRating >= $rating RETURN m ORDER BY m.feedbackRating DESC")
    List<Mentorship> findMentorshipsWithHighRatings(@Param("rating") Double rating);

    // Count mentorships by mentor
    @Query("MATCH (m:Mentorship) WHERE m.mentorId = $mentorId RETURN count(m)")
    Long countMentorshipsByMentor(@Param("mentorId") Long mentorId);

    // Count mentorships by mentee
    @Query("MATCH (m:Mentorship) WHERE m.menteeId = $menteeId RETURN count(m)")
    Long countMentorshipsByMentee(@Param("menteeId") Long menteeId);

    // Count mentorships by status
    @Query("MATCH (m:Mentorship) WHERE m.status = $status RETURN count(m)")
    Long countMentorshipsByStatus(@Param("status") String status);

    // Find mentorships by title containing
    @Query("MATCH (m:Mentorship) WHERE toLower(m.title) CONTAINS toLower($title) RETURN m")
    List<Mentorship> findByTitleContainingIgnoreCase(@Param("title") String title);
}
