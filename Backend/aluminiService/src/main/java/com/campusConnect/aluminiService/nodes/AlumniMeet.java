package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.Mode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("AlumniMeet")
public class AlumniMeet {

    @Id
    @GeneratedValue
    private Long id;

    @Property("title")
    private String title;

    @Property("mode")
    private Mode mode;

    @Property("description")
    private String description;

    @Property("location")
    private String location; // Physical location for OFFLINE/HYBRID mode

    @Property("meetingLink")
    private String meetingLink; // Online meeting link for ONLINE/HYBRID mode

    @Property("scheduledDateTime")
    private LocalDateTime scheduledDateTime;

    @Property("duration")
    private Integer duration; // Duration in minutes

    @Property("maxParticipants")
    private Integer maxParticipants;

    @Property("status")
    private String status; // SCHEDULED, ONGOING, COMPLETED, CANCELLED

    @Property("organizerId")
    private Long organizerId; // Reference to organizer User ID

    @Property("participantIds")
    private List<Long> participantIds; // References to User IDs in external service

    @Property("createdAt")
    private LocalDateTime createdAt;

    @Property("updatedAt")
    private LocalDateTime updatedAt;

    @Property("tags")
    private List<String> tags; // Tags for categorization
}
