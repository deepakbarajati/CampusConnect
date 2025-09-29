package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Placement")
public class Placement {
    @Id
    @GeneratedValue
    private Long id;

    @Property("studentId")
    private Long studentId; // External microservice reference

    @Property("company")
    private String company;

    @Property("role")
    private String role;

    @Property("salary")
    private BigDecimal salary;

    @Property("status")
    private Status status; // Neo4j handles enums as properties naturally

    @Property("interviewExperience")
    private String interviewExperience;

    // Enhanced fields
    @Property("offerDate")
    private LocalDateTime offerDate;

    @Property("joiningDate")
    private LocalDateTime joiningDate;

    @Property("location")
    private String location;

    @Property("offerType")
    private String offerType; // EX: FULL_TIME, INTERNSHIP, PPO

    @Property("createdAt")
    private LocalDateTime createdAt;

    @Property("updatedAt")
    private LocalDateTime updatedAt;
}
