package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Job;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface JobRepository extends Neo4jRepository<Job, Long> {
}