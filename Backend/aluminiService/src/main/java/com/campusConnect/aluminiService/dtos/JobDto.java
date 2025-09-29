package com.campusConnect.aluminiService.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobDto{
    private Long id;

   private  Long postedBy;

   private  String description;

    private String company;

   private  String location;

   private  String applyLink;

   private  LocalDateTime deadLine;
}