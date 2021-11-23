package com.example.security.domain.board.like.dto;

import com.example.security.domain.board.like.Like;
import com.example.security.domain.user.UserInfo;
import lombok.Data;

@Data
public class LikeResponse {

    private Long id;

    private UserInfo userInfo;

    public LikeResponse(Like like) {
        this.id = like.getId();
        this.userInfo = like.getUserInfo();
    }
}
