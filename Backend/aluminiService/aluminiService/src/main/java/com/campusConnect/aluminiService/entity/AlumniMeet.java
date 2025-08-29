package com.campusConnect.aluminiService.entity;

import com.campusConnect.aluminiService.entity.enums.Mode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AlumniMeet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Mode mode;

    //private List<> participants
}