package com.campusConnect.aluminiService.repository;

import com.campusConnect.aluminiService.nodes.Job;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JobRepository extends Neo4jRepository<Job, Long> {

    // Find jobs by posted by user
    List<Job> findByPostedBy(Long postedBy);

    // Find jobs by company
    List<Job> findByCompany(String company);

    // Find jobs by location
    List<Job> findByLocation(String location);

    // Find jobs by status
    List<Job> findByStatus(String status);

    // Find jobs by job type
    List<Job> findByJobType(String jobType);

    // Find jobs by experience level
    List<Job> findByExperienceLevel(String experienceLevel);

    // Find remote jobs
    List<Job> findByIsRemote(Boolean isRemote);

    // Find jobs by title containing (case insensitive)
    @Query("MATCH (j:Job) WHERE toLower(j.title) CONTAINS toLower($title) RETURN j")
    List<Job> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Find jobs by company containing (case insensitive)
    @Query("MATCH (j:Job) WHERE toLower(j.company) CONTAINS toLower($company) RETURN j")
    List<Job> findByCompanyContainingIgnoreCase(@Param("company") String company);

    // Find jobs with deadline after specific date
    @Query("MATCH (j:Job) WHERE j.deadLine > $date RETURN j ORDER BY j.deadLine ASC")
    List<Job> findJobsWithDeadlineAfter(@Param("date") LocalDateTime date);

    // Find jobs expiring soon (within specified days)
    @Query("MATCH (j:Job) WHERE j.deadLine > $now AND j.deadLine <= $expiryDate AND j.status = 'ACTIVE' RETURN j ORDER BY j.deadLine ASC")
    List<Job> findJobsExpiringSoon(@Param("now") LocalDateTime now,
                                   @Param("expiryDate") LocalDateTime expiryDate);

    // Find jobs by skill
    @Query("MATCH (j:Job) WHERE $skill IN j.skills RETURN j")
    List<Job> findJobsBySkill(@Param("skill") String skill);

    // Find jobs by multiple criteria
    @Query("MATCH (j:Job) WHERE " +
            "($title IS NULL OR toLower(j.title) CONTAINS toLower($title)) AND " +
            "($company IS NULL OR toLower(j.company) CONTAINS toLower($company)) AND " +
            "($location IS NULL OR j.location = $location) AND " +
            "($jobType IS NULL OR j.jobType = $jobType) AND " +
            "($experienceLevel IS NULL OR j.experienceLevel = $experienceLevel) AND " +
            "($isRemote IS NULL OR j.isRemote = $isRemote) AND " +
            "($status IS NULL OR j.status = $status) " +
            "RETURN j ORDER BY j.createdAt DESC")
    List<Job> searchJobs(@Param("title") String title,
                         @Param("company") String company,
                         @Param("location") String location,
                         @Param("jobType") String jobType,
                         @Param("experienceLevel") String experienceLevel,
                         @Param("isRemote") Boolean isRemote,
                         @Param("status") String status);

    // Find recent jobs (within specified days)
    @Query("MATCH (j:Job) WHERE j.createdAt >= $date RETURN j ORDER BY j.createdAt DESC")
    List<Job> findRecentJobs(@Param("date") LocalDateTime date);

    // Count jobs by company
    @Query("MATCH (j:Job) WHERE j.company = $company RETURN count(j)")
    Long countJobsByCompany(@Param("company") String company);

    // Count active jobs by user
    @Query("MATCH (j:Job) WHERE j.postedBy = $userId AND j.status = 'ACTIVE' RETURN count(j)")
    Long countActiveJobsByUser(@Param("userId") Long userId);
}
