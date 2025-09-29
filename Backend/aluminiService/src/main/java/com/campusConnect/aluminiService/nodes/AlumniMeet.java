package com.campusConnect.aluminiService.nodes;

import com.campusConnect.aluminiService.nodes.enums.Mode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Node
public class AlumniMeet {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private Mode mode;

    private List<Long> participantIds; // References to User IDs in external service
}
