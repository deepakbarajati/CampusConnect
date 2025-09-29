package com.campusConnect.opportunityService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

//    private Eligiblity eligiblity;
// TODO add eligibilty link or elibilty entity and increase size of this entity with fields
    private String applyLink;

    private Date deadline;
}