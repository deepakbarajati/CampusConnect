package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Placement;
import com.campusConnect.aluminiService.nodes.enums.Status;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PlacementRepository extends Neo4jRepository<Placement, Long> {
    List<Placement> findByStudentId(Long studentId);
    List<Placement> findByCompany(String company);
    List<Placement> findByRole(String role);
    List<Placement> findByStatus(Status status);

    @Query("MATCH (p:Placement) WHERE toLower(p.company) CONTAINS toLower($company) RETURN p")
    List<Placement> searchByCompany(@Param("company") String company);

    @Query("MATCH (p:Placement) WHERE toLower(p.role) CONTAINS toLower($role) RETURN p")
    List<Placement> searchByRole(@Param("role") String role);

    @Query("MATCH (p:Placement) WHERE p.salary >= $minSalary AND p.salary <= $maxSalary RETURN p")
    List<Placement> findBySalaryRange(@Param("minSalary") BigDecimal min, @Param("maxSalary") BigDecimal max);

    @Query("MATCH (p:Placement) WHERE p.offerType = $offerType RETURN p")
    List<Placement> findByOfferType(@Param("offerType") String offerType);
}
