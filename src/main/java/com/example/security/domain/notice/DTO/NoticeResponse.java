package com.example.security.domain.notice.DTO;

import com.example.security.domain.notice.Notice;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeResponse {
    private Long noticeId;

    private String author;

    private LocalDate date;

    private String title;

    private String content;

    private Long hit;

    private String important;

    public NoticeResponse(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.author = notice.getAuthor();
        this.date = notice.getDate();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.hit = notice.getHit();
        this.important = notice.getImportant();
    }
}
