package com.campusConnect.authService.service.impl;

import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.exception.ResourceNotFoundException;
import com.campusConnect.authService.repository.UserRepository;
import com.campusConnect.authService.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+userId));
    }

    @Override
    public Boolean userExistWithId(Long userId) {
        return userRepository.existsById(userId);
    }


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // identifier can be username or email
        return userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))  // try email if username fails
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + identifier));
    }
}
