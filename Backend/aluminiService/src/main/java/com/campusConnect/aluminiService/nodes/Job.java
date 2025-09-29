package com.campusConnect.aluminiService.nodes;

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
@Node("Job")
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @Property("postedBy")
    private Long postedBy; // Reference to User ID (from another microservice)

    @Property("description")
    private String description;

    @Property("company")
    private String company;

    @Property("location")
    private String location;

    @Property("applyLink")
    private String applyLink;

    @Property("deadLine")
    private LocalDateTime deadLine;

    // Enhanced fields
    @Property("title")
    private String title;

    @Property("jobType")
    private String jobType; // FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP

    @Property("salaryRange")
    private String salaryRange; // e.g., "50000-80000", "Not disclosed"

    @Property("experienceLevel")
    private String experienceLevel; // ENTRY, MID, SENIOR, EXECUTIVE

    @Property("skills")
    private List<String> skills; // Required skills

    @Property("isRemote")
    private Boolean isRemote;

    @Property("status")
    private String status; // ACTIVE, CLOSED, DRAFT, EXPIRED

    @Property("applicationCount")
    private Integer applicationCount;

    @Property("createdAt")
    private LocalDateTime createdAt;

    @Property("updatedAt")
    private LocalDateTime updatedAt;
}
