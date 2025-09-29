package com.campusConnect.aluminiService.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Industry is required")
    private String industry;

    @NotBlank(message = "Location is required")
    private String location;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Pattern(regexp = "^(https?://)?(www\\.)?[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/.*)?$",
            message = "Invalid website URL format")
    private String website;

    @Min(value = 1800, message = "Founded year must be after 1800")
    @Max(value = 2100, message = "Founded year must be before 2100")
    private Integer foundedYear;

    private String employeeSize;

    private String logoUrl;

    @Email(message = "Invalid email format")
    private String contactEmail;

    private String phoneNumber;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
