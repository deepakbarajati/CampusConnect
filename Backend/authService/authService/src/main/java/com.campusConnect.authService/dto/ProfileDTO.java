package com.campusConnect.authService.dto;

import com.campusConnect.authService.entity.enums.Branch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileDTO {

    @NotNull(message = "UserId is compulsory")
    private Long userId;

    @NotBlank(message = "First name cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name cannot be blank")
    private String lastname;

    @NotBlank(message = "Bio cannot be blank")
    private String bio;

    @NotNull(message = "Branch is compulsory")
    private Branch branch;

    @NotNull(message = "Year is compulsory")
    private Integer year;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    private List<String> skills;

    private List<String> links;

    private List<String> achievements;
}
