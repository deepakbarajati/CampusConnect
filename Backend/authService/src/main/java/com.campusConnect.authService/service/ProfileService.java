package com.campusConnect.authService.service;

import com.campusConnect.authService.dto.ProfileDTO;
import com.campusConnect.authService.entity.enums.Branch;
import org.springframework.http.HttpStatusCode;


import java.util.List;

public interface ProfileService {

    ProfileDTO getProfileById(Long profileId);

    ProfileDTO deleteProfileById(Long profileId);

     ProfileDTO updateProfileById(Long profileId, ProfileDTO profileDTO);

    ProfileDTO profileCreation(ProfileDTO profileDTO) ;

    List<ProfileDTO> getAllProfile();

    List<ProfileDTO> getAllProfileByBranch(Branch branch);

    ProfileDTO getProfileByUserId(Long userId);
}
