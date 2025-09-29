package com.campusConnect.aluminiService.service.impl;

import com.campusConnect.aluminiService.dto.CompanyDTO;
import com.campusConnect.aluminiService.nodes.Company;
import com.campusConnect.aluminiService.repository.CompanyRepository;
import com.campusConnect.aluminiService.service.CompanyService;
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
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtils mapperUtils;

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        log.info("Creating new company with name: {}", companyDTO.getName());

        // Check if company name already exists
        if (companyRepository.existsByName(companyDTO.getName())) {
            throw new RuntimeException("Company with name '" + companyDTO.getName() + "' already exists");
        }

        Company company = mapperUtils.map(companyDTO, Company.class);
        company.setCreatedAt(LocalDateTime.now());
        company.setUpdatedAt(LocalDateTime.now());

        // Set default status if not provided
        if (company.getStatus() == null || company.getStatus().isEmpty()) {
            company.setStatus("ACTIVE");
        }

        Company savedCompany = companyRepository.save(company);
        log.info("Company created successfully with ID: {}", savedCompany.getId());

        return mapperUtils.map(savedCompany, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyById(Long id) {
        log.info("Fetching company with ID: {}", id);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));

        return mapperUtils.map(company, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getAllCompanies() {
        log.info("Fetching all companies");

        List<Company> companies = companyRepository.findAll();
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        log.info("Updating company with ID: {}", id);

        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));

        // Check if name is being changed and if new name already exists
        if (!existingCompany.getName().equals(companyDTO.getName()) &&
                companyRepository.existsByName(companyDTO.getName())) {
            throw new RuntimeException("Company with name '" + companyDTO.getName() + "' already exists");
        }

        // Update fields
        existingCompany.setName(companyDTO.getName());
        existingCompany.setIndustry(companyDTO.getIndustry());
        existingCompany.setLocation(companyDTO.getLocation());
        existingCompany.setDescription(companyDTO.getDescription());
        existingCompany.setWebsite(companyDTO.getWebsite());
        existingCompany.setFoundedYear(companyDTO.getFoundedYear());
        existingCompany.setEmployeeSize(companyDTO.getEmployeeSize());
        existingCompany.setLogoUrl(companyDTO.getLogoUrl());
        existingCompany.setContactEmail(companyDTO.getContactEmail());
        existingCompany.setPhoneNumber(companyDTO.getPhoneNumber());
        existingCompany.setStatus(companyDTO.getStatus());
        existingCompany.setUpdatedAt(LocalDateTime.now());

        Company updatedCompany = companyRepository.save(existingCompany);
        log.info("Company updated successfully with ID: {}", updatedCompany.getId());

        return mapperUtils.map(updatedCompany, CompanyDTO.class);
    }

    @Override
    public void deleteCompany(Long id) {
        log.info("Deleting company with ID: {}", id);

        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Company not found with ID: " + id);
        }

        companyRepository.deleteById(id);
        log.info("Company deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDTO getCompanyByName(String name) {
        log.info("Fetching company with name: {}", name);

        Company company = companyRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Company not found with name: " + name));

        return mapperUtils.map(company, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesByIndustry(String industry) {
        log.info("Fetching companies in industry: {}", industry);

        List<Company> companies = companyRepository.findByIndustry(industry);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesByLocation(String location) {
        log.info("Fetching companies in location: {}", location);

        List<Company> companies = companyRepository.findByLocation(location);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesByStatus(String status) {
        log.info("Fetching companies with status: {}", status);

        List<Company> companies = companyRepository.findByStatus(status);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesByEmployeeSize(String employeeSize) {
        log.info("Fetching companies with employee size: {}", employeeSize);

        List<Company> companies = companyRepository.findByEmployeeSize(employeeSize);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> searchCompaniesByName(String name) {
        log.info("Searching companies with name: {}", name);

        List<Company> companies = companyRepository.findByNameContainingIgnoreCase(name);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesByIndustryAndLocation(String industry, String location) {
        log.info("Fetching companies with industry: {} and location: {}", industry, location);

        List<Company> companies = companyRepository.findByIndustryAndLocation(industry, location);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesFoundedAfter(Integer year) {
        log.info("Fetching companies founded after: {}", year);

        List<Company> companies = companyRepository.findCompaniesFoundedAfter(year);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> getCompaniesFoundedBetween(Integer startYear, Integer endYear) {
        log.info("Fetching companies founded between: {} and {}", startYear, endYear);

        List<Company> companies = companyRepository.findCompaniesFoundedBetween(startYear, endYear);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDTO> searchCompanies(String name, String industry, String location, String status) {
        log.info("Searching companies with criteria - name: {}, industry: {}, location: {}, status: {}",
                name, industry, location, status);

        List<Company> companies = companyRepository.searchCompanies(name, industry, location, status);
        return mapperUtils.mapList(companies, CompanyDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return companyRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByIndustry(String industry) {
        return companyRepository.countByIndustry(industry);
    }

    @Override
    public CompanyDTO updateCompanyStatus(Long id, String status) {
        log.info("Updating status of company {} to {}", id, status);

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with ID: " + id));

        company.setStatus(status);
        company.setUpdatedAt(LocalDateTime.now());

        Company updatedCompany = companyRepository.save(company);
        return mapperUtils.map(updatedCompany, CompanyDTO.class);
    }
}
