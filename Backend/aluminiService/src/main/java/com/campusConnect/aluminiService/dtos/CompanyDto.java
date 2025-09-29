package com.campusConnect.aluminiService.dtos;

import lombok.Data;

@Data
public class CompanyDto {
    private Long id;

    private String name;

    private String industry;

    private String location;
}