package com.campusConnect.authService.entity;


import com.campusConnect.authService.entity.enums.Branch;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String bio;

    @Enumerated(EnumType.STRING)
    private Branch branch;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String designation;

    private List<String> skills;

    private List<String> links;

    private List<String> achievements;
}
