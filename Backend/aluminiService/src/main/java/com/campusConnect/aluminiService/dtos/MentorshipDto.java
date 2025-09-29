package com.campusConnect.aluminiService.dtos;

import com.campusConnect.aluminiService.nodes.enums.MentorShipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class MentorshipDto {
    private Long Id;

    private Long MenteeId;

   private  Date StartDate;

    private Date EndDate;

    private MentorShipStatus Status;
}