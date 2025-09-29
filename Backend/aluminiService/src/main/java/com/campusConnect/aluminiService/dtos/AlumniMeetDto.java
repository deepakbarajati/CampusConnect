package com.campusConnect.aluminiService.dtos;

import com.campusConnect.aluminiService.nodes.enums.Mode;
import lombok.Data;

@Data
public class AlumniMeetDto {
    private Long id;

    private String title;

    private Mode mode;
}