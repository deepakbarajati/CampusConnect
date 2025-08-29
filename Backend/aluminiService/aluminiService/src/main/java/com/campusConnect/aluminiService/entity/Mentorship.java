package com.campusConnect.aluminiService.entity;

import com.campusConnect.aluminiService.entity.enums.MentorShipStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Mentorship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long mentorId;

    private Long menteeId;

    private Date startDate;

    private Date endDate;

    private MentorShipStatus status;
}