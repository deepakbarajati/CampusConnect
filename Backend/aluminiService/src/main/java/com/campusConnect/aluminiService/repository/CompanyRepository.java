package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface CompanyRepository extends Neo4jRepository<Company, Long> {
}