package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.AlumniMeet;
import com.campusConnect.aluminiService.nodes.enums.Mode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlumniMeetRepository extends Neo4jRepository<AlumniMeet, Long> {

    // Find meets by organizer
    List<AlumniMeet> findByOrganizerId(Long organizerId);

    // Find meets by mode
    List<AlumniMeet> findByMode(Mode mode);

    // Find meets by status
    List<AlumniMeet> findByStatus(String status);

    // Find meets containing participant
    @Query("MATCH (am:AlumniMeet) WHERE $participantId IN am.participantIds RETURN am")
    List<AlumniMeet> findByParticipantId(@Param("participantId") Long participantId);

    // Find upcoming meets
    @Query("MATCH (am:AlumniMeet) WHERE am.scheduledDateTime > $currentTime RETURN am ORDER BY am.scheduledDateTime ASC")
    List<AlumniMeet> findUpcomingMeets(@Param("currentTime") LocalDateTime currentTime);

    // Find meets scheduled between dates
    @Query("MATCH (am:AlumniMeet) WHERE am.scheduledDateTime >= $startDate AND am.scheduledDateTime <= $endDate RETURN am ORDER BY am.scheduledDateTime ASC")
    List<AlumniMeet> findMeetsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);

    // Find meets by title containing
    @Query("MATCH (am:AlumniMeet) WHERE toLower(am.title) CONTAINS toLower($title) RETURN am")
    List<AlumniMeet> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Find meets by tag
    @Query("MATCH (am:AlumniMeet) WHERE $tag IN am.tags RETURN am")
    List<AlumniMeet> findByTag(@Param("tag") String tag);

    // Count participants in a meet
    @Query("MATCH (am:AlumniMeet) WHERE id(am) = $meetId RETURN size(am.participantIds)")
    Integer countParticipants(@Param("meetId") Long meetId);

    // Find meets organized by user with specific status
    @Query("MATCH (am:AlumniMeet) WHERE am.organizerId = $organizerId AND am.status = $status RETURN am")
    List<AlumniMeet> findByOrganizerIdAndStatus(@Param("organizerId") Long organizerId,
                                                @Param("status") String status);
}
