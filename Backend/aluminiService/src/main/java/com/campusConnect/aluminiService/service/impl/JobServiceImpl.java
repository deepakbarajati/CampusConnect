package com.campusConnect.aluminiService.service.impl;

import com.campusConnect.aluminiService.dto.JobDTO;
import com.campusConnect.aluminiService.nodes.Job;
import com.campusConnect.aluminiService.repository.JobRepository;
import com.campusConnect.aluminiService.service.JobService;
import com.campusConnect.aluminiService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final MapperUtils mapperUtils;

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        log.info("Creating new job with title: {}", jobDTO.getTitle());

        Job job = mapperUtils.map(jobDTO, Job.class);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());

        // Set default values
        if (job.getStatus() == null || job.getStatus().isEmpty()) {
            job.setStatus("ACTIVE");
        }
        if (job.getApplicationCount() == null) {
            job.setApplicationCount(0);
        }
        if (job.getIsRemote() == null) {
            job.setIsRemote(false);
        }

        Job savedJob = jobRepository.save(job);
        log.info("Job created successfully with ID: {}", savedJob.getId());

        return mapperUtils.map(savedJob, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public JobDTO getJobById(Long id) {
        log.info("Fetching job with ID: {}", id);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

        return mapperUtils.map(job, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getAllJobs() {
        log.info("Fetching all jobs");

        List<Job> jobs = jobRepository.findAll();
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        log.info("Updating job with ID: {}", id);

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

        // Update fields
        existingJob.setTitle(jobDTO.getTitle());
        existingJob.setDescription(jobDTO.getDescription());
        existingJob.setCompany(jobDTO.getCompany());
        existingJob.setLocation(jobDTO.getLocation());
        existingJob.setApplyLink(jobDTO.getApplyLink());
        existingJob.setDeadLine(jobDTO.getDeadLine());
        existingJob.setJobType(jobDTO.getJobType());
        existingJob.setSalaryRange(jobDTO.getSalaryRange());
        existingJob.setExperienceLevel(jobDTO.getExperienceLevel());
        existingJob.setSkills(jobDTO.getSkills());
        existingJob.setIsRemote(jobDTO.getIsRemote());
        existingJob.setStatus(jobDTO.getStatus());
        existingJob.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(existingJob);
        log.info("Job updated successfully with ID: {}", updatedJob.getId());

        return mapperUtils.map(updatedJob, JobDTO.class);
    }

    @Override
    public void deleteJob(Long id) {
        log.info("Deleting job with ID: {}", id);

        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found with ID: " + id);
        }

        jobRepository.deleteById(id);
        log.info("Job deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByPostedBy(Long postedBy) {
        log.info("Fetching jobs posted by user: {}", postedBy);

        List<Job> jobs = jobRepository.findByPostedBy(postedBy);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByCompany(String company) {
        log.info("Fetching jobs by company: {}", company);

        List<Job> jobs = jobRepository.findByCompany(company);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByLocation(String location) {
        log.info("Fetching jobs by location: {}", location);

        List<Job> jobs = jobRepository.findByLocation(location);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByStatus(String status) {
        log.info("Fetching jobs with status: {}", status);

        List<Job> jobs = jobRepository.findByStatus(status);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByJobType(String jobType) {
        log.info("Fetching jobs with job type: {}", jobType);

        List<Job> jobs = jobRepository.findByJobType(jobType);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsByExperienceLevel(String experienceLevel) {
        log.info("Fetching jobs with experience level: {}", experienceLevel);

        List<Job> jobs = jobRepository.findByExperienceLevel(experienceLevel);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getRemoteJobs(Boolean isRemote) {
        log.info("Fetching jobs with remote status: {}", isRemote);

        List<Job> jobs = jobRepository.findByIsRemote(isRemote);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> searchJobsByTitle(String title) {
        log.info("Searching jobs with title: {}", title);

        List<Job> jobs = jobRepository.findByTitleContainingIgnoreCase(title);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> searchJobsByCompany(String company) {
        log.info("Searching jobs with company: {}", company);

        List<Job> jobs = jobRepository.findByCompanyContainingIgnoreCase(company);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsWithDeadlineAfter(LocalDateTime date) {
        log.info("Fetching jobs with deadline after: {}", date);

        List<Job> jobs = jobRepository.findJobsWithDeadlineAfter(date);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsExpiringSoon(int days) {
        log.info("Fetching jobs expiring within {} days", days);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = now.plusDays(days);

        List<Job> jobs = jobRepository.findJobsExpiringSoon(now, expiryDate);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getJobsBySkill(String skill) {
        log.info("Fetching jobs requiring skill: {}", skill);

        List<Job> jobs = jobRepository.findJobsBySkill(skill);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> searchJobs(String title, String company, String location, String jobType,
                                   String experienceLevel, Boolean isRemote, String status) {
        log.info("Searching jobs with criteria - title: {}, company: {}, location: {}, jobType: {}, experienceLevel: {}, isRemote: {}, status: {}",
                title, company, location, jobType, experienceLevel, isRemote, status);

        List<Job> jobs = jobRepository.searchJobs(title, company, location, jobType, experienceLevel, isRemote, status);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobDTO> getRecentJobs(int days) {
        log.info("Fetching jobs created within {} days", days);

        LocalDateTime date = LocalDateTime.now().minusDays(days);
        List<Job> jobs = jobRepository.findRecentJobs(date);
        return mapperUtils.mapList(jobs, JobDTO.class);
    }

    @Override
    public JobDTO updateJobStatus(Long id, String status) {
        log.info("Updating status of job {} to {}", id, status);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

        job.setStatus(status);
        job.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);
        return mapperUtils.map(updatedJob, JobDTO.class);
    }

    @Override
    public JobDTO incrementApplicationCount(Long id) {
        log.info("Incrementing application count for job: {}", id);

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

        int currentCount = job.getApplicationCount() != null ? job.getApplicationCount() : 0;
        job.setApplicationCount(currentCount + 1);
        job.setUpdatedAt(LocalDateTime.now());

        Job updatedJob = jobRepository.save(job);
        return mapperUtils.map(updatedJob, JobDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countJobsByCompany(String company) {
        return jobRepository.countJobsByCompany(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countActiveJobsByUser(Long userId) {
        return jobRepository.countActiveJobsByUser(userId);
    }

    @Override
    public void closeExpiredJobs() {
        log.info("Closing expired jobs");

        List<Job> expiredJobs = jobRepository.findJobsWithDeadlineAfter(LocalDateTime.now());
        expiredJobs.stream()
                .filter(job -> job.getDeadLine().isBefore(LocalDateTime.now()))
                .filter(job -> "ACTIVE".equals(job.getStatus()))
                .forEach(job -> {
                    job.setStatus("EXPIRED");
                    job.setUpdatedAt(LocalDateTime.now());
                    jobRepository.save(job);
                });

        log.info("Expired jobs have been closed");
    }
}
