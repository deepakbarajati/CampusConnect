package com.campusConnect.aluminiService.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Node
public class Job {
    @Id
    @GeneratedValue
    private Long id;

    private Long postedBy; // Reference to User ID (from another microservice)

    private String description;

    private String company;

    private String location;

    private String applyLink;

    private LocalDateTime deadLine;
}
