package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Placement;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PlacementRepository extends Neo4jRepository<Placement, Long> {
}