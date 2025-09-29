package com.campusConnect.aluminiService.nodes;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Node
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Uncomment and implement when ready:
    // private Type type;

    private String industry;

    private String location;
}
