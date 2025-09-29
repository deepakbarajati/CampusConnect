package com.campusConnect.aluminiService.service.impl;

import com.campusConnect.aluminiService.dto.PlacementDTO;
import com.campusConnect.aluminiService.nodes.Placement;
import com.campusConnect.aluminiService.nodes.enums.Status;
import com.campusConnect.aluminiService.repository.PlacementRepository;
import com.campusConnect.aluminiService.service.PlacementService;
import com.campusConnect.aluminiService.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlacementServiceImpl implements PlacementService {

    private final PlacementRepository placementRepository;
    private final MapperUtils mapperUtils;

    @Override
    public PlacementDTO createPlacement(PlacementDTO placementDTO) {
        log.info("Creating new placement for student: {}", placementDTO.getStudentId());
        Placement placement = mapperUtils.map(placementDTO, Placement.class);
        placement.setCreatedAt(LocalDateTime.now());
        placement.setUpdatedAt(LocalDateTime.now());
        Placement saved = placementRepository.save(placement);
        return mapperUtils.map(saved, PlacementDTO.class);
    }

    @Override
    public PlacementDTO getPlacementById(Long id) {
        Placement placement = placementRepository.findById(id).orElseThrow(() -> new RuntimeException("Placement not found!"));
        return mapperUtils.map(placement, PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getAllPlacements() {
        return mapperUtils.mapList(placementRepository.findAll(), PlacementDTO.class);
    }

    @Override
    public PlacementDTO updatePlacement(Long id, PlacementDTO placementDTO) {
        Placement placement = placementRepository.findById(id).orElseThrow(() -> new RuntimeException("Placement not found!"));
        placement.setCompany(placementDTO.getCompany());
        placement.setRole(placementDTO.getRole());
        placement.setSalary(placementDTO.getSalary());
        placement.setStatus(placementDTO.getStatus());
        placement.setInterviewExperience(placementDTO.getInterviewExperience());
        placement.setOfferDate(placementDTO.getOfferDate());
        placement.setJoiningDate(placementDTO.getJoiningDate());
        placement.setLocation(placementDTO.getLocation());
        placement.setOfferType(placementDTO.getOfferType());
        placement.setUpdatedAt(LocalDateTime.now());
        Placement updated = placementRepository.save(placement);
        return mapperUtils.map(updated, PlacementDTO.class);
    }

    @Override
    public void deletePlacement(Long id) {
        placementRepository.deleteById(id);
    }

    @Override
    public List<PlacementDTO> getPlacementsByStudentId(Long studentId) {
        return mapperUtils.mapList(placementRepository.findByStudentId(studentId), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getPlacementsByCompany(String company) {
        return mapperUtils.mapList(placementRepository.findByCompany(company), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getPlacementsByRole(String role) {
        return mapperUtils.mapList(placementRepository.findByRole(role), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getPlacementsByStatus(Status status) {
        return mapperUtils.mapList(placementRepository.findByStatus(status), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> searchPlacementsByCompany(String company) {
        return mapperUtils.mapList(placementRepository.searchByCompany(company), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> searchPlacementsByRole(String role) {
        return mapperUtils.mapList(placementRepository.searchByRole(role), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getPlacementsBySalaryRange(BigDecimal min, BigDecimal max) {
        return mapperUtils.mapList(placementRepository.findBySalaryRange(min, max), PlacementDTO.class);
    }

    @Override
    public List<PlacementDTO> getPlacementsByOfferType(String offerType) {
        return mapperUtils.mapList(placementRepository.findByOfferType(offerType), PlacementDTO.class);
    }
}
