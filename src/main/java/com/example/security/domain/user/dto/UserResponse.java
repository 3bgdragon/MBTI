package com.example.security.domain.user.dto;

import com.example.security.domain.user.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long code;

    private String email;

    private String auth;

    private String mbti;

    private String status;

    private LocalDateTime lastLoginDt;

    public UserResponse(UserInfo user) {
        this.code = user.getCode();
        this.email = user.getEmail();
        this.auth = user.getAuth();
        this.mbti = user.getMbti();
        this.status = user.getStatus();
        this.lastLoginDt = user.getLastLoginDt();
    }
}
