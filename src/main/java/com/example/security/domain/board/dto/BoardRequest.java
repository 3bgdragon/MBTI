package com.example.security.domain.board.dto;

import com.example.security.domain.board.Board;
import com.example.security.domain.user.UserInfo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardRequest {

    private Long id;

    private String mbti;

    private String group;

    private String all;

    private UserInfo userInfo;

    private String title;

    private String content;

    private LocalDateTime date;

    private Long hit;

    private String important;

    public Board toEntity() {
        return Board.builder()
                .id(this.id)
                .mbti(this.mbti)
                .group(this.group)
                .all(this.all)
                .userInfo(this.userInfo)
                .title(this.title)
                .content(this.content)
                .date(this.date)
                .hit(this.hit)
                .important(this.important)
                .build();
    }
}
