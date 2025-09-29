package com.campusConnect.opportunityService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
@Entity
@Table(name = "eligibility_criteria")
public class EligibilityCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    private Long minGPA;

    private Year year;

    private String branch;

    //TODO add skillsRequired

}