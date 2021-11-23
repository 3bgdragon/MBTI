package com.example.security.domain.board.like.dto;

import com.example.security.domain.board.Board;
import com.example.security.domain.board.like.Like;
import com.example.security.domain.user.UserInfo;
import lombok.Data;

@Data
public class LikeRequest {

    private Long id;

    private Board board;

    private UserInfo userInfo;

    public Like toEntity() {
        return Like.builder()
                .id(this.id)
                .board(this.board)
                .userInfo(this.userInfo)
                .build();
    }
}
