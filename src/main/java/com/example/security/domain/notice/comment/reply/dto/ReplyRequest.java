package com.example.security.domain.notice.comment.reply.dto;

import com.example.security.domain.notice.comment.NoticeComment;
import com.example.security.domain.notice.comment.reply.CommentReply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyRequest {
    private Long id;

    private NoticeComment noticeComment;

    private String content;

    private String author;

    private LocalDateTime date;

    public CommentReply toEntity() {
        return CommentReply.builder()
                .id(this.id)
                .noticeComment(this.noticeComment)
                .content(this.content)
                .author(this.author)
                .date(this.date)
                .build();
    }
}
