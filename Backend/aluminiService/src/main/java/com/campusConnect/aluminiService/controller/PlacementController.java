package com.campusConnect.aluminiService.controller;

import com.campusConnect.aluminiService.dto.PlacementDTO;
import com.campusConnect.aluminiService.nodes.enums.Status;
import com.campusConnect.aluminiService.service.PlacementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/placements")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PlacementController {

    private final PlacementService placementService;

    @PostMapping
    public ResponseEntity<PlacementDTO> createPlacement(@Valid @RequestBody PlacementDTO placementDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(placementService.createPlacement(placementDTO));
    }

    @GetMapping
    public ResponseEntity<List<PlacementDTO>> getAllPlacements() {
        return ResponseEntity.ok(placementService.getAllPlacements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlacementDTO> getPlacementById(@PathVariable Long id) {
        return ResponseEntity.ok(placementService.getPlacementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlacementDTO> updatePlacement(@PathVariable Long id, @Valid @RequestBody PlacementDTO placementDTO) {
        return ResponseEntity.ok(placementService.updatePlacement(id, placementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlacement(@PathVariable Long id) {
        placementService.deletePlacement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(placementService.getPlacementsByStudentId(studentId));
    }

    @GetMapping("/company/{company}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByCompany(@PathVariable String company) {
        return ResponseEntity.ok(placementService.getPlacementsByCompany(company));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByRole(@PathVariable String role) {
        return ResponseEntity.ok(placementService.getPlacementsByRole(role));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(placementService.getPlacementsByStatus(status));
    }

    @GetMapping("/search/company")
    public ResponseEntity<List<PlacementDTO>> searchPlacementsByCompany(@RequestParam String company) {
        return ResponseEntity.ok(placementService.searchPlacementsByCompany(company));
    }

    @GetMapping("/search/role")
    public ResponseEntity<List<PlacementDTO>> searchPlacementsByRole(@RequestParam String role) {
        return ResponseEntity.ok(placementService.searchPlacementsByRole(role));
    }

    @GetMapping("/salary-range")
    public ResponseEntity<List<PlacementDTO>> getPlacementsBySalaryRange(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(placementService.getPlacementsBySalaryRange(min, max));
    }

    @GetMapping("/offer-type/{offerType}")
    public ResponseEntity<List<PlacementDTO>> getPlacementsByOfferType(@PathVariable String offerType) {
        return ResponseEntity.ok(placementService.getPlacementsByOfferType(offerType));
    }
}
