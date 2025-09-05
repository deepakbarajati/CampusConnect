package com.campusConnect.authService.repository;

import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByRole(Role role);
}
