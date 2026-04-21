package com.campusConnect.authService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Token is required")
    private String token;

    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String newPassword;
}
