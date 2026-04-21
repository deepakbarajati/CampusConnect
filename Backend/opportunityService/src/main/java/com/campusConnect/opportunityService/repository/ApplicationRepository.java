package com.campusConnect.opportunityService.repository;

import com.campusConnect.opportunityService.entity.Application;
import com.campusConnect.opportunityService.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStatus(Status status);
    List<Application> findByOpportunityId(Long opportunityId);
}
