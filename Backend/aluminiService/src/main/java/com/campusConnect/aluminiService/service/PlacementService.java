package com.campusConnect.aluminiService.service;

import com.campusConnect.aluminiService.dto.PlacementDTO;
import com.campusConnect.aluminiService.nodes.enums.Status;

import java.math.BigDecimal;
import java.util.List;

public interface PlacementService {
    PlacementDTO createPlacement(PlacementDTO placementDTO);
    PlacementDTO getPlacementById(Long id);
    List<PlacementDTO> getAllPlacements();
    PlacementDTO updatePlacement(Long id, PlacementDTO placementDTO);
    void deletePlacement(Long id);

    List<PlacementDTO> getPlacementsByStudentId(Long studentId);
    List<PlacementDTO> getPlacementsByCompany(String company);
    List<PlacementDTO> getPlacementsByRole(String role);
    List<PlacementDTO> getPlacementsByStatus(Status status);
    List<PlacementDTO> searchPlacementsByCompany(String company);
    List<PlacementDTO> searchPlacementsByRole(String role);
    List<PlacementDTO> getPlacementsBySalaryRange(BigDecimal min, BigDecimal max);
    List<PlacementDTO> getPlacementsByOfferType(String offerType);
}
