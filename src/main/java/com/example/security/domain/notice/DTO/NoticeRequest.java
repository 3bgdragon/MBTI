package com.example.security.domain.notice.DTO;

import com.example.security.domain.notice.Notice;
import lombok.Data;

@Data
public class NoticeRequest {

    private Long noticeId;

    private String author;

    private String title;

    private String content;

    private String important;

    public Notice toEntity() {
        return Notice.builder()
                .noticeId(this.noticeId)
                .author(this.author)
                .title(this.title)
                .content(this.content)
                .important(this.important)
                .build();
    }
}
