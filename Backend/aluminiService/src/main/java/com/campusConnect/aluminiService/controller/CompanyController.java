package com.campusConnect.aluminiService.controller;

import com.campusConnect.aluminiService.dto.CompanyDTO;
import com.campusConnect.aluminiService.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    // Create company
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        log.info("Request to create company: {}", companyDTO.getName());

        try {
            CompanyDTO createdCompany = companyService.createCompany(companyDTO);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating company: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all companies
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        log.info("Request to get all companies");

        try {
            List<CompanyDTO> companies = companyService.getAllCompanies();
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all companies: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get company by ID
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        log.info("Request to get company with ID: {}", id);

        try {
            CompanyDTO company = companyService.getCompanyById(id);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching company with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Update company
    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id,
                                                    @Valid @RequestBody CompanyDTO companyDTO) {
        log.info("Request to update company with ID: {}", id);

        try {
            CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating company with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete company
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        log.info("Request to delete company with ID: {}", id);

        try {
            companyService.deleteCompany(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting company with ID {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get company by name
    @GetMapping("/name/{name}")
    public ResponseEntity<CompanyDTO> getCompanyByName(@PathVariable String name) {
        log.info("Request to get company with name: {}", name);

        try {
            CompanyDTO company = companyService.getCompanyByName(name);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching company with name {}: {}", name, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get companies by industry
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByIndustry(@PathVariable String industry) {
        log.info("Request to get companies in industry: {}", industry);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesByIndustry(industry);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies by industry {}: {}", industry, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies by location
    @GetMapping("/location/{location}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByLocation(@PathVariable String location) {
        log.info("Request to get companies in location: {}", location);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesByLocation(location);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies by location {}: {}", location, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByStatus(@PathVariable String status) {
        log.info("Request to get companies with status: {}", status);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesByStatus(status);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies by status {}: {}", status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies by employee size
    @GetMapping("/employee-size/{size}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByEmployeeSize(@PathVariable String size) {
        log.info("Request to get companies with employee size: {}", size);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesByEmployeeSize(size);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies by employee size {}: {}", size, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Search companies by name
    @GetMapping("/search/name")
    public ResponseEntity<List<CompanyDTO>> searchCompaniesByName(@RequestParam String name) {
        log.info("Request to search companies with name: {}", name);

        try {
            List<CompanyDTO> companies = companyService.searchCompaniesByName(name);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching companies by name {}: {}", name, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies by industry and location
    @GetMapping("/filter")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByIndustryAndLocation(
            @RequestParam String industry,
            @RequestParam String location) {
        log.info("Request to get companies with industry: {} and location: {}", industry, location);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesByIndustryAndLocation(industry, location);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies by industry and location: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies founded after specific year
    @GetMapping("/founded-after/{year}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesFoundedAfter(@PathVariable Integer year) {
        log.info("Request to get companies founded after: {}", year);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesFoundedAfter(year);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies founded after {}: {}", year, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get companies founded between years
    @GetMapping("/founded-between")
    public ResponseEntity<List<CompanyDTO>> getCompaniesFoundedBetween(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear) {
        log.info("Request to get companies founded between: {} and {}", startYear, endYear);

        try {
            List<CompanyDTO> companies = companyService.getCompaniesFoundedBetween(startYear, endYear);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching companies founded between years: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Advanced search
    @GetMapping("/search")
    public ResponseEntity<List<CompanyDTO>> searchCompanies(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String status) {
        log.info("Request to search companies with criteria - name: {}, industry: {}, location: {}, status: {}",
                name, industry, location, status);

        try {
            List<CompanyDTO> companies = companyService.searchCompanies(name, industry, location, status);
            return new ResponseEntity<>(companies, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error searching companies: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Check if company name exists
    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        log.info("Request to check if company name exists: {}", name);

        try {
            boolean exists = companyService.existsByName(name);
            return new ResponseEntity<>(exists, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error checking company name existence {}: {}", name, e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Count companies by industry
    @GetMapping("/count/industry/{industry}")
    public ResponseEntity<Long> countByIndustry(@PathVariable String industry) {
        log.info("Request to count companies in industry: {}", industry);

        try {
            Long count = companyService.countByIndustry(industry);
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error counting companies by industry {}: {}", industry, e.getMessage());
            return new ResponseEntity<>(0L, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update company status
    @PatchMapping("/{id}/status")
    public ResponseEntity<CompanyDTO> updateCompanyStatus(@PathVariable Long id,
                                                          @RequestParam String status) {
        log.info("Request to update status of company {} to {}", id, status);

        try {
            CompanyDTO updatedCompany = companyService.updateCompanyStatus(id, status);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating status of company {} to {}: {}", id, status, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
