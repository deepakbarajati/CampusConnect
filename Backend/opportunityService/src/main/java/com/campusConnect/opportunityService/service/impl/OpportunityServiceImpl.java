package com.campusConnect.opportunityService.service.impl;

import com.campusConnect.opportunityService.dto.ApplicationDTO;
import com.campusConnect.opportunityService.dto.OpportunityDTO;
import com.campusConnect.opportunityService.entity.Application;
import com.campusConnect.opportunityService.entity.Opportunity;
import com.campusConnect.opportunityService.entity.enums.Status;
import com.campusConnect.opportunityService.repository.ApplicationRepository;
import com.campusConnect.opportunityService.repository.OpportunityRepository;
import com.campusConnect.opportunityService.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public OpportunityDTO createOpportunity(OpportunityDTO opportunityDTO) {
        log.info("Creating opportunity: {}", opportunityDTO.getTitle());
        Opportunity opportunity = modelMapper.map(opportunityDTO, Opportunity.class);
        Opportunity saved = opportunityRepository.save(opportunity);
        return modelMapper.map(saved, OpportunityDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public OpportunityDTO getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
        return modelMapper.map(opportunity, OpportunityDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpportunityDTO> getAllOpportunities() {
        return opportunityRepository.findAll().stream()
                .map(o -> modelMapper.map(o, OpportunityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OpportunityDTO updateOpportunity(Long id, OpportunityDTO opportunityDTO) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
        opportunity.setTitle(opportunityDTO.getTitle());
        opportunity.setDescription(opportunityDTO.getDescription());
        opportunity.setApplyLink(opportunityDTO.getApplyLink());
        opportunity.setDeadline(opportunityDTO.getDeadline());
        Opportunity saved = opportunityRepository.save(opportunity);
        return modelMapper.map(saved, OpportunityDTO.class);
    }

    @Override
    public void deleteOpportunity(Long id) {
        opportunityRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpportunityDTO> getActiveOpportunities() {
        return opportunityRepository.findByDeadlineAfter(new Date()).stream()
                .map(o -> modelMapper.map(o, OpportunityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpportunityDTO> searchOpportunities(String title) {
        return opportunityRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(o -> modelMapper.map(o, OpportunityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO applyForOpportunity(ApplicationDTO applicationDTO) {
        Opportunity opportunity = opportunityRepository.findById(applicationDTO.getOpportunityId())
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
        Application application = modelMapper.map(applicationDTO, Application.class);
        application.setOpportunity(opportunity);
        application.setStatus(Status.PENDING);
        Application saved = applicationRepository.save(application);
        return modelMapper.map(saved, ApplicationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByOpportunity(Long opportunityId) {
        return applicationRepository.findByOpportunityId(opportunityId).stream()
                .map(a -> modelMapper.map(a, ApplicationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicationsByStatus(String status) {
        Status statusEnum = Status.valueOf(status.toUpperCase());
        return applicationRepository.findByStatus(statusEnum).stream()
                .map(a -> modelMapper.map(a, ApplicationDTO.class))
                .collect(Collectors.toList());
    }
}
