package com.campusConnect.authService.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String identifier;
    private String password;
}
