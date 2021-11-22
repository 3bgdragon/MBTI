package com.example.security.domain.notice.comment.dto;

import com.example.security.domain.notice.comment.NoticeComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;

    private String content;

    private String author;

    private LocalDateTime date;

    public CommentResponse(NoticeComment noticeComment) {
        this.id = noticeComment.getId();
        this.content = noticeComment.getContent();
        this.author = noticeComment.getAuthor();
        this.date = noticeComment.getDate();
    }
}
