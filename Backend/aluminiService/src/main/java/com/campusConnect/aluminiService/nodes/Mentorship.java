package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Mentorship")
public class Mentorship {

    @Id
    @GeneratedValue
    private Long id;

    @Property("mentorId")
    private Long mentorId; // Reference to user in external microservice

    @Property("menteeId")
    private Long menteeId; // Reference to user in external microservice

    @Property("startDate")
    private Date startDate;

    @Property("endDate")
    private Date endDate;

    @Property("status")
    private MentorShipStatus status; // Neo4j stores enum as property

    // Enhanced fields
    @Property("title")
    private String title;

    @Property("description")
    private String description;

    @Property("goals")
    private List<String> goals;

    @Property("meetingFrequency")
    private String meetingFrequency; // WEEKLY, BIWEEKLY, MONTHLY

    @Property("meetingMode")
    private String meetingMode; // ONLINE, OFFLINE, HYBRID

    @Property("sessionCount")
    private Integer sessionCount;

    @Property("feedbackRating")
    private Double feedbackRating; // 1-5 rating

    @Property("createdAt")
    private Date createdAt;

    @Property("updatedAt")
    private Date updatedAt;
}
