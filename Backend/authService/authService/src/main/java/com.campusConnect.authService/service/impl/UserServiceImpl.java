package com.campusConnect.authService.service.impl;


import com.campusConnect.authService.dto.UserDTO;
import com.campusConnect.authService.entity.User;
import com.campusConnect.authService.entity.enums.Role;
import com.campusConnect.authService.exception.ResourceNotFoundException;
import com.campusConnect.authService.repository.ProfileRepository;
import com.campusConnect.authService.repository.UserRepository;
import com.campusConnect.authService.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    public UserDTO userCreation(UserDTO userDTO) {
        User user=modelMapper.map(userDTO,User.class);
        user=userRepository.save(user);
        userDTO=modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users=userRepository.findAll();

        return users
                .stream()
                .map((element) -> modelMapper.map(element, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllUserByRole(Role role) {
        List<User> users =userRepository.findByRole(role);
        return users
                .stream()
                .map((element) -> modelMapper.map(element, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+userId));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO deleteUserById(Long userId) {
        profileRepository.deleteByUserId(userId);
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+userId));
                userRepository.delete(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO updateUserById(Long userId,UserDTO userDTO) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found with ID: "+userId));
        user=modelMapper.map(userDTO, User.class);
        user.setId(userId);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }


}
