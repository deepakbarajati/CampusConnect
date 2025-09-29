package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.Date;

@Getter
@Setter
@Node
public class Mentorship {
    @Id
    @GeneratedValue
    private Long id;

    private Long mentorId; // Reference to user in external microservice

    private Long menteeId; // Reference to user in external microservice

    private Date startDate;

    private Date endDate;

    private MentorShipStatus status; // Neo4j stores enum as property
}
