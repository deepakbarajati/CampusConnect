package com.campusConnect.opportunityService.controller;

import com.campusConnect.opportunityService.dto.ApplicationDTO;
import com.campusConnect.opportunityService.dto.OpportunityDTO;
import com.campusConnect.opportunityService.service.OpportunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OpportunityController {

    private final OpportunityService opportunityService;

    // Opportunity endpoints
    @PostMapping
    public ResponseEntity<OpportunityDTO> createOpportunity(@Valid @RequestBody OpportunityDTO opportunityDTO) {
        log.info("Creating opportunity: {}", opportunityDTO.getTitle());
        OpportunityDTO created = opportunityService.createOpportunity(opportunityDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OpportunityDTO>> getAllOpportunities() {
        List<OpportunityDTO> opportunities = opportunityService.getAllOpportunities();
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDTO> getOpportunityById(@PathVariable Long id) {
        OpportunityDTO opportunity = opportunityService.getOpportunityById(id);
        return ResponseEntity.ok(opportunity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityDTO> updateOpportunity(@PathVariable Long id, @Valid @RequestBody OpportunityDTO opportunityDTO) {
        OpportunityDTO updated = opportunityService.updateOpportunity(id, opportunityDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        opportunityService.deleteOpportunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    public ResponseEntity<List<OpportunityDTO>> getActiveOpportunities() {
        List<OpportunityDTO> opportunities = opportunityService.getActiveOpportunities();
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OpportunityDTO>> searchOpportunities(@RequestParam String title) {
        List<OpportunityDTO> opportunities = opportunityService.searchOpportunities(title);
        return ResponseEntity.ok(opportunities);
    }

    // Application endpoints
    @PostMapping("/apply")
    public ResponseEntity<ApplicationDTO> applyForOpportunity(@Valid @RequestBody ApplicationDTO applicationDTO) {
        ApplicationDTO created = opportunityService.applyForOpportunity(applicationDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{opportunityId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByOpportunity(@PathVariable Long opportunityId) {
        List<ApplicationDTO> applications = opportunityService.getApplicationsByOpportunity(opportunityId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/applications/status/{status}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByStatus(@PathVariable String status) {
        List<ApplicationDTO> applications = opportunityService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }
}
