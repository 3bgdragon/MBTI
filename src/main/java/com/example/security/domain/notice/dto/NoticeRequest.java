package com.example.security.domain.notice.dto;

import com.example.security.domain.notice.Notice;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class NoticeRequest {

    private Long noticeId;

    private String author;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate date = LocalDate.parse(LocalDate.now().format(formatter));

    private String title;

    private String content;

    private Long hit;

    private String important;

    public Notice toEntity() {
        return Notice.builder()
                .noticeId(this.noticeId)
                .author(this.author)
                .date(this.date)
                .title(this.title)
                .content(this.content)
                .hit(this.hit)
                .important(this.important)
                .build();
    }
}
