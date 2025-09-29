package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.Status;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Node
public class Placement {
    @Id
    @GeneratedValue
    private Long id;

    private Long studentId; // External microservice reference

    private String company;

    private String role;

    private BigDecimal salary;

    private Status status; // Neo4j handles enums as properties naturally

    private String interviewExperience;
}
