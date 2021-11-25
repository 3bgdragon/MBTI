package com.example.security.domain.user.dto;

import com.example.security.domain.user.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRequest {

    private Long code;

    private String email;

    private String password;

    private String auth;

    private String mbti;

    private String status;

    private LocalDateTime lastLoginDt;

    public UserInfo toEntity() {
        return UserInfo.builder()
                .code(this.code)
                .email(this.email)
                .password(this.password)
                .auth(this.auth)
                .mbti(this.mbti)
                .status(this.status)
                .lastLoginDt(this.lastLoginDt)
                .build();
    }
}

