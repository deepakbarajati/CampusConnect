package com.campusConnect.aluminiService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Company {

    // CHECK WHEN CREATE API THIS NEEDED OR NOT
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // private Type  type;  when impliment this entitys api

    private String industry;

    private String location;
}
