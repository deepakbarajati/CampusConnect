package com.campusConnect.authService.controller;

import com.campusConnect.authService.dto.ProfileDTO;

import com.campusConnect.authService.entity.enums.Branch;
import com.campusConnect.authService.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping()
    public ResponseEntity<ProfileDTO> profileCreation(@Valid @RequestBody ProfileDTO profileDTO) {
        return new ResponseEntity<>(profileService.profileCreation(profileDTO), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<ProfileDTO>> getAllProfile(){
        return new ResponseEntity<>(profileService.getAllProfile(),HttpStatus.OK);
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<ProfileDTO>> getAllUserByRole(@PathVariable Branch branch){
        return new ResponseEntity<>(profileService.getAllProfileByBranch(branch),HttpStatus.OK);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long profileId){
        return new ResponseEntity<>(profileService.getProfileById(profileId),HttpStatus.FOUND);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> deleteProfileById(@PathVariable Long profileId){
        return new ResponseEntity<>(profileService.deleteProfileById(profileId),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> updateProfileById(@PathVariable Long profileId, @Valid @RequestBody ProfileDTO profileDTO ){
        return new ResponseEntity<>(profileService.updateProfileById(profileId,profileDTO),HttpStatus.ACCEPTED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ProfileDTO> getProfileByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(profileService.getProfileByUserId(userId),HttpStatus.ACCEPTED);
    }
}
