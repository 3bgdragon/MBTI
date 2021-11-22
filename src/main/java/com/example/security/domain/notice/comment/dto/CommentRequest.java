package com.example.security.domain.notice.comment.dto;

import com.example.security.domain.notice.Notice;
import com.example.security.domain.notice.comment.NoticeComment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequest {
    private Long id;

    private Notice notice;

    private String content;

    private String author;

    private LocalDateTime date;

    public NoticeComment toEntity() {
        return NoticeComment.builder()
                .id(this.id)
                .notice(this.notice)
                .content(this.content)
                .author(this.author)
                .date(this.date)
                .build();
    }
}
