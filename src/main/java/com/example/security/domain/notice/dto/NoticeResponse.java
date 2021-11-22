package com.example.security.domain.notice.dto;

import com.example.security.domain.notice.Notice;
import com.example.security.domain.notice.comment.dto.CommentResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NoticeResponse {
    private Long noticeId;

    private String author;

    private LocalDate date;

    private String title;

    private String content;

    private Long hit;

    private String important;

    private List<CommentResponse> childList;

    public NoticeResponse(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.author = notice.getAuthor();
        this.date = notice.getDate();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.hit = notice.getHit();
        this.important = notice.getImportant();
        this.childList = notice.getChildList().stream().map(e -> new CommentResponse(e))
                .collect(Collectors.toList());
    }
}
