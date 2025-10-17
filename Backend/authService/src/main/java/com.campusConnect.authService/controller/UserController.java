package com.campusConnect.authService.controller;

import com.campusConnect.authService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/{userId}")
    Boolean userExistWithId(@PathVariable Long userId){
        log.info("Coming in this function");
        return userService.userExistWithId(userId);
    }
}