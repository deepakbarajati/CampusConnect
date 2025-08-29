package com.campusConnect.authService.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private Year year;

    @Column(nullable = false)
    private String designation;

    private List<String> skills;

    private List<String> link;

    private List<String> achievements;
}
