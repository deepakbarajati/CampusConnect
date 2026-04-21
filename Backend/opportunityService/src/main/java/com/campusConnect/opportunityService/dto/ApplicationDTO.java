package com.campusConnect.opportunityService.dto;

import com.campusConnect.opportunityService.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private Long id;

    @NotNull(message = "Opportunity ID is required")
    private Long opportunityId;

    private Status status;
}
