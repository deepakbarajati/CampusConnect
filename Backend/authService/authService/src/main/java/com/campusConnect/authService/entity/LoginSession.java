package com.campusConnect.authService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LoginSession {
    @Id
    private String token;

    private String deviceInfo;

    private LocalDateTime expiry;
}