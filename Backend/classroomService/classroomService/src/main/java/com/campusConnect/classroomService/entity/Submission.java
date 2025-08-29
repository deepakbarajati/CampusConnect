package com.campusConnect.classroomService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    private String studentId;

    private String file_url;

    private String grade;
}