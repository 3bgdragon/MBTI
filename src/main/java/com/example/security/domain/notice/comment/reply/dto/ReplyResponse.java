package com.example.security.domain.notice.comment.reply.dto;

import com.example.security.domain.notice.comment.reply.CommentReply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyResponse {
    private Long id;

    private String content;

    private String author;

    private LocalDateTime date;

    public ReplyResponse(CommentReply commentReply) {
        this.id = commentReply.getId();
        this.content = commentReply.getContent();
        this.author = commentReply.getAuthor();
        this.date = commentReply.getDate();
    }
}
