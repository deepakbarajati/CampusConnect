package com.campusConnect.classroomService.entity;

import com.campusConnect.classroomService.entity.enums.ExamType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="classroom_id")
    private Classroom classroom;

    @Enumerated(EnumType.STRING)
    private ExamType type;

    private Date date;

    private Float marks;
}