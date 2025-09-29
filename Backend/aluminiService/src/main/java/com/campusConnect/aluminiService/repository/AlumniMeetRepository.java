package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.AlumniMeet;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface AlumniMeetRepository extends Neo4jRepository<AlumniMeet, Long> {
}