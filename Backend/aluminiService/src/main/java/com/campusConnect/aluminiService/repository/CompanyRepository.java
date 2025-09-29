package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Company;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends Neo4jRepository<Company, Long> {

    // Find companies by name (exact match)
    Optional<Company> findByName(String name);

    // Find companies by industry
    List<Company> findByIndustry(String industry);

    // Find companies by location
    List<Company> findByLocation(String location);

    // Find companies by status
    List<Company> findByStatus(String status);

    // Find companies by employee size
    List<Company> findByEmployeeSize(String employeeSize);

    // Find companies by name containing (case insensitive)
    @Query("MATCH (c:Company) WHERE toLower(c.name) CONTAINS toLower($name) RETURN c")
    List<Company> findByNameContainingIgnoreCase(@Param("name") String name);

    // Find companies by industry and location
    List<Company> findByIndustryAndLocation(String industry, String location);

    // Find companies founded after specific year
    @Query("MATCH (c:Company) WHERE c.foundedYear >= $year RETURN c ORDER BY c.foundedYear DESC")
    List<Company> findCompaniesFoundedAfter(@Param("year") Integer year);

    // Find companies founded between years
    @Query("MATCH (c:Company) WHERE c.foundedYear >= $startYear AND c.foundedYear <= $endYear RETURN c ORDER BY c.foundedYear DESC")
    List<Company> findCompaniesFoundedBetween(@Param("startYear") Integer startYear,
                                              @Param("endYear") Integer endYear);

    // Search companies by multiple criteria
    @Query("MATCH (c:Company) WHERE " +
            "($name IS NULL OR toLower(c.name) CONTAINS toLower($name)) AND " +
            "($industry IS NULL OR c.industry = $industry) AND " +
            "($location IS NULL OR c.location = $location) AND " +
            "($status IS NULL OR c.status = $status) " +
            "RETURN c")
    List<Company> searchCompanies(@Param("name") String name,
                                  @Param("industry") String industry,
                                  @Param("location") String location,
                                  @Param("status") String status);

    // Check if company name exists
    boolean existsByName(String name);

    // Count companies by industry
    @Query("MATCH (c:Company) WHERE c.industry = $industry RETURN count(c)")
    Long countByIndustry(@Param("industry") String industry);
}
