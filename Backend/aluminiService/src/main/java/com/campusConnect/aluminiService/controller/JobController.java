package com.campusConnect.aluminiService.controller;

import com.campusConnect.aluminiService.dto.JobDTO;
import com.campusConnect.aluminiService.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    // Create job
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO) {
        log.info("Request to create job: {}", jobDTO.getTitle());

        try {
            JobDTO createdJob = jobService.createJob(jobDTO);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating job: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        log.info("Request to get all jobs");

        try {
            List<JobDTO> jobs = jobService.getAllJobs();
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all jobs: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        log.info("Request to get job with ID: {}", id);

        try {
            JobDTO job = jobService.getJobById(id);
            return new ResponseEntity<>(job, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching job with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update job
    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id,
                                            @Valid @RequestBody JobDTO jobDTO) {
        log.info("Request to update job with ID: {}", id);

        try {
            JobDTO updatedJob = jobService.updateJob(id, jobDTO);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating job with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete job
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.info("Request to delete job with ID: {}", id);

        try {
            jobService.deleteJob(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting job with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get jobs by posted by user
    @GetMapping("/posted-by/{userId}")
    public ResponseEntity<List<JobDTO>> getJobsByPostedBy(@PathVariable Long userId) {
        log.info("Request to get jobs posted by user: {}", userId);

        try {
            List<JobDTO> jobs = jobService.getJobsByPostedBy(userId);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by posted by {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by company
    @GetMapping("/company/{company}")
    public ResponseEntity<List<JobDTO>> getJobsByCompany(@PathVariable String company) {
        log.info("Request to get jobs by company: {}", company);

        try {
            List<JobDTO> jobs = jobService.getJobsByCompany(company);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by company {}: {}", company, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<JobDTO>> getJobsByLocation(@PathVariable String location) {
        log.info("Request to get jobs by location: {}", location);

        try {
            List<JobDTO> jobs = jobService.getJobsByLocation(location);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by location {}: {}", location, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobDTO>> getJobsByStatus(@PathVariable String status) {
        log.info("Request to get jobs with status: {}", status);

        try {
            List<JobDTO> jobs = jobService.getJobsByStatus(status);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by status {}: {}", status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by job type
    @GetMapping("/job-type/{jobType}")
    public ResponseEntity<List<JobDTO>> getJobsByJobType(@PathVariable String jobType) {
        log.info("Request to get jobs with job type: {}", jobType);

        try {
            List<JobDTO> jobs = jobService.getJobsByJobType(jobType);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by job type {}: {}", jobType, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by experience level
    @GetMapping("/experience/{experienceLevel}")
    public ResponseEntity<List<JobDTO>> getJobsByExperienceLevel(@PathVariable String experienceLevel) {
        log.info("Request to get jobs with experience level: {}", experienceLevel);

        try {
            List<JobDTO> jobs = jobService.getJobsByExperienceLevel(experienceLevel);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by experience level {}: {}", experienceLevel, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get remote jobs
    @GetMapping("/remote/{isRemote}")
    public ResponseEntity<List<JobDTO>> getRemoteJobs(@PathVariable Boolean isRemote) {
        log.info("Request to get jobs with remote status: {}", isRemote);

        try {
            List<JobDTO> jobs = jobService.getRemoteJobs(isRemote);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching remote jobs: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search jobs by title
    @GetMapping("/search/title")
    public ResponseEntity<List<JobDTO>> searchJobsByTitle(@RequestParam String title) {
        log.info("Request to search jobs with title: {}", title);

        try {
            List<JobDTO> jobs = jobService.searchJobsByTitle(title);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching jobs by title {}: {}", title, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search jobs by company
    @GetMapping("/search/company")
    public ResponseEntity<List<JobDTO>> searchJobsByCompany(@RequestParam String company) {
        log.info("Request to search jobs with company: {}", company);

        try {
            List<JobDTO> jobs = jobService.searchJobsByCompany(company);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching jobs by company {}: {}", company, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs with deadline after specific date
    @GetMapping("/deadline-after")
    public ResponseEntity<List<JobDTO>> getJobsWithDeadlineAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        log.info("Request to get jobs with deadline after: {}", date);

        try {
            List<JobDTO> jobs = jobService.getJobsWithDeadlineAfter(date);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs with deadline after {}: {}", date, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs expiring soon
    @GetMapping("/expiring-soon")
    public ResponseEntity<List<JobDTO>> getJobsExpiringSoon(@RequestParam(defaultValue = "7") int days) {
        log.info("Request to get jobs expiring within {} days", days);

        try {
            List<JobDTO> jobs = jobService.getJobsExpiringSoon(days);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs expiring soon: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get jobs by skill
    @GetMapping("/skill/{skill}")
    public ResponseEntity<List<JobDTO>> getJobsBySkill(@PathVariable String skill) {
        log.info("Request to get jobs requiring skill: {}", skill);

        try {
            List<JobDTO> jobs = jobService.getJobsBySkill(skill);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching jobs by skill {}: {}", skill, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Advanced search
    @GetMapping("/search")
    public ResponseEntity<List<JobDTO>> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) Boolean isRemote,
            @RequestParam(required = false) String status) {
        log.info("Request to search jobs with criteria - title: {}, company: {}, location: {}, jobType: {}, experienceLevel: {}, isRemote: {}, status: {}",
                title, company, location, jobType, experienceLevel, isRemote, status);

        try {
            List<JobDTO> jobs = jobService.searchJobs(title, company, location, jobType, experienceLevel, isRemote, status);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching jobs: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get recent jobs
    @GetMapping("/recent")
    public ResponseEntity<List<JobDTO>> getRecentJobs(@RequestParam(defaultValue = "30") int days) {
        log.info("Request to get jobs created within {} days", days);

        try {
            List<JobDTO> jobs = jobService.getRecentJobs(days);
            return new ResponseEntity<>(jobs, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching recent jobs: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update job status
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobDTO> updateJobStatus(@PathVariable Long id,
                                                  @RequestParam String status) {
        log.info("Request to update status of job {} to {}", id, status);

        try {
            JobDTO updatedJob = jobService.updateJobStatus(id, status);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating status of job {} to {}: {}", id, status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Increment application count
    @PostMapping("/{id}/apply")
    public ResponseEntity<JobDTO> incrementApplicationCount(@PathVariable Long id) {
        log.info("Request to increment application count for job: {}", id);

        try {
            JobDTO updatedJob = jobService.incrementApplicationCount(id);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error incrementing application count for job {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Count jobs by company
    @GetMapping("/count/company/{company}")
    public ResponseEntity<Long> countJobsByCompany(@PathVariable String company) {
        log.info("Request to count jobs by company: {}", company);

        try {
            Long count = jobService.countJobsByCompany(company);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting jobs by company {}: {}", company, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count active jobs by user
    @GetMapping("/count/user/{userId}/active")
    public ResponseEntity<Long> countActiveJobsByUser(@PathVariable Long userId) {
        log.info("Request to count active jobs by user: {}", userId);

        try {
            Long count = jobService.countActiveJobsByUser(userId);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting active jobs by user {}: {}", userId, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Close expired jobs
    @PostMapping("/close-expired")
    public ResponseEntity<String> closeExpiredJobs() {
        log.info("Request to close expired jobs");

        try {
            jobService.closeExpiredJobs();
            return new ResponseEntity<>("Expired jobs have been closed", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error closing expired jobs: {}", e.getMessage());
            return new ResponseEntity<>("Error closing expired jobs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
