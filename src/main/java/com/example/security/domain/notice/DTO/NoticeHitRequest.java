package com.example.security.domain.notice.DTO;

import com.example.security.domain.notice.Notice;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class NoticeHitRequest {

    private Long noticeId;

    private String author;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDate date = LocalDate.parse(LocalDate.now().format(formatter));

    private String title;

    private String content;

    private Long hit;

    private String important;

    public Notice toEntity(NoticeResponse response) {
        return Notice.builder()
                .noticeId(response.getNoticeId())
                .author(response.getAuthor())
                .date(response.getDate())
                .title(response.getTitle())
                .content(response.getContent())
                .hit(response.getHit())
                .important(response.getImportant())
                .build();
    }
}
