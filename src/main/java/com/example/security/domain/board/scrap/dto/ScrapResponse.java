package com.example.security.domain.board.scrap.dto;

import com.example.security.domain.board.scrap.Scrap;
import com.example.security.domain.user.UserInfo;
import lombok.Data;

@Data
public class ScrapResponse {

    private Long id;

    private UserInfo userInfo;

    public ScrapResponse(Scrap scrap) {
        this.id = scrap.getId();
        this.userInfo = scrap.getUserInfo();
    }
}
