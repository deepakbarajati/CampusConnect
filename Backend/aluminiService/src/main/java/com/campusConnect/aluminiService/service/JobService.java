package com.campusConnect.aluminiService.service;

import com.campusConnect.aluminiService.dto.JobDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface JobService {

    // CRUD Operations
    JobDTO createJob(JobDTO jobDTO);
    JobDTO getJobById(Long id);
    List<JobDTO> getAllJobs();
    JobDTO updateJob(Long id, JobDTO jobDTO);
    void deleteJob(Long id);

    // Query Operations
    List<JobDTO> getJobsByPostedBy(Long postedBy);
    List<JobDTO> getJobsByCompany(String company);
    List<JobDTO> getJobsByLocation(String location);
    List<JobDTO> getJobsByStatus(String status);
    List<JobDTO> getJobsByJobType(String jobType);
    List<JobDTO> getJobsByExperienceLevel(String experienceLevel);
    List<JobDTO> getRemoteJobs(Boolean isRemote);
    List<JobDTO> searchJobsByTitle(String title);
    List<JobDTO> searchJobsByCompany(String company);
    List<JobDTO> getJobsWithDeadlineAfter(LocalDateTime date);
    List<JobDTO> getJobsExpiringSoon(int days);
    List<JobDTO> getJobsBySkill(String skill);
    List<JobDTO> searchJobs(String title, String company, String location, String jobType,
                            String experienceLevel, Boolean isRemote, String status);
    List<JobDTO> getRecentJobs(int days);

    // Utility Methods
    JobDTO updateJobStatus(Long id, String status);
    JobDTO incrementApplicationCount(Long id);
    Long countJobsByCompany(String company);
    Long countActiveJobsByUser(Long userId);
    void closeExpiredJobs();
}
