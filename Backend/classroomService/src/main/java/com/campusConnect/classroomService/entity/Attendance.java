package com.campusConnect.classroomService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long student_id;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    private Date date;

    private Boolean isPresent;
}
