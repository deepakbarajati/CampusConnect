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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("Company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    // Uncomment and implement when ready:
    // private Type type;

    @Property("industry")
    private String industry;

    @Property("location")
    private String location;

    // Enhanced fields
    @Property("description")
    private String description;

    @Property("website")
    private String website;

    @Property("foundedYear")
    private Integer foundedYear;

    @Property("employeeSize")
    private String employeeSize; // e.g., "1-10", "11-50", "51-200", "201-500", "500+"

    @Property("logoUrl")
    private String logoUrl;

    @Property("contactEmail")
    private String contactEmail;

    @Property("phoneNumber")
    private String phoneNumber;

    @Property("status")
    private String status; // ACTIVE, INACTIVE, VERIFIED, PENDING

    @Property("createdAt")
    private LocalDateTime createdAt;

    @Property("updatedAt")
    private LocalDateTime updatedAt;
}
