package com.campusConnect.aluminiService.service;

import com.campusConnect.aluminiService.dto.CompanyDTO;

import java.util.List;

public interface CompanyService {

    // CRUD Operations
    CompanyDTO createCompany(CompanyDTO companyDTO);
    CompanyDTO getCompanyById(Long id);
    List<CompanyDTO> getAllCompanies();
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    void deleteCompany(Long id);

    // Query Operations
    CompanyDTO getCompanyByName(String name);
    List<CompanyDTO> getCompaniesByIndustry(String industry);
    List<CompanyDTO> getCompaniesByLocation(String location);
    List<CompanyDTO> getCompaniesByStatus(String status);
    List<CompanyDTO> getCompaniesByEmployeeSize(String employeeSize);
    List<CompanyDTO> searchCompaniesByName(String name);
    List<CompanyDTO> getCompaniesByIndustryAndLocation(String industry, String location);
    List<CompanyDTO> getCompaniesFoundedAfter(Integer year);
    List<CompanyDTO> getCompaniesFoundedBetween(Integer startYear, Integer endYear);
    List<CompanyDTO> searchCompanies(String name, String industry, String location, String status);

    // Utility Methods
    boolean existsByName(String name);
    Long countByIndustry(String industry);
    CompanyDTO updateCompanyStatus(Long id, String status);
}
