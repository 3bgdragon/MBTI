package com.example.security.domain.board.dto;

import com.example.security.domain.board.Board;
import com.example.security.domain.board.like.dto.LikeResponse;
import com.example.security.domain.board.scrap.dto.ScrapResponse;
import com.example.security.domain.user.dto.UserResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BoardResponse {

    private Long id;

    private String mbti;

    private String group;

    private String all;

    private UserResponse user;

    private String title;

    private String content;

    private LocalDateTime date;

    private Long hit;

    private String important;

    private List<LikeResponse> likeList;

    private List<ScrapResponse> scrapList;

    public BoardResponse(Board board) {
        this.id = board.getId();
        this.mbti = board.getMbti();
        this.group = board.getGroup();
        this.all = board.getAll();
        this.user = new UserResponse(board.getUserInfo());
        this.title = board.getTitle();
        this.content = board.getContent();
        this.date = board.getDate();
        this.hit = board.getHit();
        this.important = board.getImportant();

        this.likeList = board.getLikeList().stream().map(e -> new LikeResponse(e))
                .collect(Collectors.toList());

        this.scrapList = board.getScrapList().stream().map(e -> new ScrapResponse(e))
                .collect(Collectors.toList());
    }
}
