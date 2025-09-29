package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Mentorship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MentorshipRepository extends Neo4jRepository<Mentorship, Long> {
}