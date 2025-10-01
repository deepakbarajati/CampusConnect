package com.campusConnect.classroomService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Time;
import java.time.DayOfWeek;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayDTO {

    @NotNull(message = "Day of week is required")
    private DayOfWeek day;

    @NotNull(message = "Start time is required")
    private Time startTime;

    @NotNull(message = "End time is required")
    private Time endTime;
}
