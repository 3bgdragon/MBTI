package com.example.security.domain.board.scrap.dto;

import com.example.security.domain.board.Board;
import com.example.security.domain.board.scrap.Scrap;
import com.example.security.domain.user.UserInfo;
import lombok.Data;

@Data
public class ScrapRequest {

    private Long id;

    private Board board;

    private UserInfo userInfo;

    public Scrap toEntity() {
        return Scrap.builder()
                .id(this.id)
                .board(this.board)
                .userInfo(this.userInfo)
                .build();
    }
}
