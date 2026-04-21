package com.campusConnect.opportunityService.repository;

import com.campusConnect.opportunityService.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByDeadlineAfter(Date date);
    List<Opportunity> findByTitleContainingIgnoreCase(String title);
}
