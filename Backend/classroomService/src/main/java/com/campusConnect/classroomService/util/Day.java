package com.campusConnect.classroomService.util;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.DayOfWeek;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Day{
    private DayOfWeek day;
    private Time startTime;
    private Time endTime;
}
