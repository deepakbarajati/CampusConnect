package com.campusConnect.authService.controller;

import com.campusConnect.authService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<Boolean> userExistWithId(@PathVariable Long userId){
        Boolean isExist=userService.userExistWithId(userId);
        return ResponseEntity.ok(isExist);
    }
}