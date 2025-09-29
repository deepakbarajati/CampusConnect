package com.campusConnect.authService.service;

import com.campusConnect.authService.entity.User;


public interface UserService {

    User getUserById(Long userId);

    Boolean userExistWithId(Long userId);
}
