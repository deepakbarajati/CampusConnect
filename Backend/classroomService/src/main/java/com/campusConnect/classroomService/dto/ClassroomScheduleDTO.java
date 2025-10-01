package com.campusConnect.classroomService.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomScheduleDTO {

    private Long id;

    @NotEmpty(message = "At least one day schedule must be provided")
    @Valid
    private List<DayDTO> day;
}
