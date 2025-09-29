package com.campusConnect.classroomService.entity;

import com.campusConnect.classroomService.entity.enums.Type;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.boot.model.internal.Nullability;

@Getter
@Setter
@Entity
@Table(name = "resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long uploadedBy;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    private String fileUrl;

    private String subject;

    private Integer semester;
}