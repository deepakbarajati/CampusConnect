package com.campusConnect.authService.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDTO {
    @NotBlank(message = "userName not be blank")
    private String username;

    @Email(message = "write a valid email")
    private String email;

    @Size(min = 8, max=16,message = "password must be between 8 and 16 characters")
    private String password;
}
