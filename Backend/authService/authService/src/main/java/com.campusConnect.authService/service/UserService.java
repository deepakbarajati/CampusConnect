package com.campusConnect.authService.service;

import com.campusConnect.authService.dto.UserDTO;
import com.campusConnect.authService.entity.enums.Role;

import java.util.List;

public interface UserService {
    UserDTO userCreation(UserDTO userDTO);

    List<UserDTO> getAllUser();

    List<UserDTO> getAllUserByRole(Role role);

    UserDTO getUserById(Long userId);

    UserDTO deleteUserById(Long userId);

    UserDTO updateUserById(Long userId,UserDTO userDTO);
}
