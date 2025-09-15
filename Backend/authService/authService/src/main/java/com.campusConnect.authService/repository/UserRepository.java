package com.campusConnect.authService.repository;

import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByRole(Role role);


     Optional<User> findByUsername(String username);

     Optional<User> findByEmail(String identifier);
}
