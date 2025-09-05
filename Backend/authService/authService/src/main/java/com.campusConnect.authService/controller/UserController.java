package com.campusConnect.authService.controller;


import com.campusConnect.authService.dto.UserDTO;
import com.campusConnect.authService.entity.enums.Role;
import com.campusConnect.authService.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> userCreation(@Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.userCreation(userDTO), HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getAllUserByRole(@PathVariable Role role){
        return new ResponseEntity<>(userService.getAllUserByRole(role),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDTO> deleteUserById(@PathVariable Long userId){
        return new ResponseEntity<>(userService.deleteUserById(userId),HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO ){
        return new ResponseEntity<>(userService.updateUserById(userId,userDTO),HttpStatus.ACCEPTED);
    }


}
