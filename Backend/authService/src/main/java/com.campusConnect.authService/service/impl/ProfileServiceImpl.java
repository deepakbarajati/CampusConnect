package com.campusConnect.authService.service.impl;

import com.campusConnect.authService.dto.ProfileDTO;
import com.campusConnect.authService.entity.Profile;
import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Branch;
import com.campusConnect.authService.exception.ResourceNotFoundException;
import com.campusConnect.authService.exception.UnAuthorisedException;
import com.campusConnect.authService.repository.ProfileRepository;
import com.campusConnect.authService.repository.UserRepository;
import com.campusConnect.authService.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ModelMapper modelMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Override
    public ProfileDTO profileCreation(ProfileDTO profileDTO) {
        User user=userRepository.findById(profileDTO.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not found"));
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user1.equals(user)){
            throw new UnAuthorisedException("This user does not own this profile with id: "+profileDTO.getUserId());
        }
        Profile profile= modelMapper.map(profileDTO, Profile.class);
        profile.setUser(user);
        profile=profileRepository.save(profile);
        profileDTO=modelMapper.map(profile, ProfileDTO.class);
        return profileDTO;
    }

    @Override
    public List<ProfileDTO> getAllProfile() {
        List<Profile> profiles=profileRepository.findAll();

        return profiles
                .stream()
                .map((element) -> modelMapper.map(element, ProfileDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProfileDTO> getAllProfileByBranch(Branch branch) {
        List<Profile> profiles =profileRepository.findByBranch(branch);
        return profiles
                .stream()
                .map((element) -> modelMapper.map(element, ProfileDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDTO getProfileByUserId(Long userId) {
        Profile profile=profileRepository.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Profile Not found with userId: "+userId));
        return modelMapper.map(profile, ProfileDTO.class);
    }

    @Override
    public ProfileDTO getProfileById(Long profileId) {
        Profile profile=profileRepository.findById(profileId).orElseThrow(()->new ResourceNotFoundException("Profile Not found with ID: "+profileId));

        return modelMapper.map(profile, ProfileDTO.class);
    }

    @Override
    public ProfileDTO deleteProfileById(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile Not found with ID: " + profileId));
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!profile.getUser().getId().equals(currentUser.getId())) {
            throw new UnAuthorisedException("This user does not own this profile with id: " + profileId);
        }
        profileRepository.delete(profile);
        return modelMapper.map(profile, ProfileDTO.class);
    }

    @Transactional
    public ProfileDTO updateProfileById(Long profileId, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile Not found with ID: " + profileId));
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!profile.getUser().getId().equals(currentUser.getId())) {
            throw new UnAuthorisedException("This user does not own this profile with id: " + profileId);
        }
        profile.setFirstname(profileDTO.getFirstname());
        profile.setLastname(profileDTO.getLastname());
        profile.setBio(profileDTO.getBio());
        profile.setBranch(profileDTO.getBranch());
        profile.setYear(profileDTO.getYear());
        profile.setDesignation(profileDTO.getDesignation());
        profile.setSkills(profileDTO.getSkills());
        profile.setLinks(profileDTO.getLinks());
        profile.setAchievements(profileDTO.getAchievements());
        Profile updatedProfile = profileRepository.save(profile);
        ProfileDTO response = modelMapper.map(updatedProfile, ProfileDTO.class);
        response.setUserId(updatedProfile.getUser().getId());

        return response;
    }


}
