package com.campusConnect.authService.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Size(min = 8, max=16,message = "password must be between 8 and 16 characters")
    private String oldPassword;
    @Size(min = 8, max=16,message = "password must be between 8 and 16 characters")
    private String newPassword;
}
