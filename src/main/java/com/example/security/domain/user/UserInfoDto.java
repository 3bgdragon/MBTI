package com.example.security.domain.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserInfoDto {
    private String code;

    private String email;

    private String password;

    private String auth;

    private String mbti;

    private String status;

    private LocalDateTime lastLoginDt;
}
