package com.campusConnect.authService.repository;

import com.campusConnect.authService.entity.Profile;
import com.campusConnect.authService.entity.enums.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    List<Profile> findByBranch(Branch branch);



    void deleteByUserId(Long userId);

    Optional<Profile> findByUserId(Long userId);
}
