package com.campusConnect.classroomService.dto;

import com.campusConnect.classroomService.entity.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    private Long id;

    @NotNull(message = "Uploaded by user ID is required")
    private Long uploadedBy;

    @NotNull(message = "Resource type is required")
    private Type type;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @NotBlank(message = "File URL is required")
    private String fileUrl;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotNull(message = "Semester is required")
    @Positive(message = "Semester must be positive")
    private Integer semester;
}
