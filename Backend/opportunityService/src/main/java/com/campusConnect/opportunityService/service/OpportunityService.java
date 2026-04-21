package com.campusConnect.opportunityService.service;

import com.campusConnect.opportunityService.dto.ApplicationDTO;
import com.campusConnect.opportunityService.dto.OpportunityDTO;

import java.util.List;

public interface OpportunityService {
    // Opportunity operations
    OpportunityDTO createOpportunity(OpportunityDTO opportunityDTO);
    OpportunityDTO getOpportunityById(Long id);
    List<OpportunityDTO> getAllOpportunities();
    OpportunityDTO updateOpportunity(Long id, OpportunityDTO opportunityDTO);
    void deleteOpportunity(Long id);
    List<OpportunityDTO> getActiveOpportunities();
    List<OpportunityDTO> searchOpportunities(String title);

    // Application operations
    ApplicationDTO applyForOpportunity(ApplicationDTO applicationDTO);
    List<ApplicationDTO> getApplicationsByOpportunity(Long opportunityId);
    List<ApplicationDTO> getApplicationsByStatus(String status);
}
