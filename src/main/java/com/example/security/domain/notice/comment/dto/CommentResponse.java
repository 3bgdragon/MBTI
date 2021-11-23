package com.example.security.domain.notice.comment.dto;

import com.example.security.domain.notice.comment.NoticeComment;
import com.example.security.domain.notice.comment.reply.dto.ReplyResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentResponse {
    private Long id;

    private String content;

    private String author;

    private LocalDateTime date;

    private List<ReplyResponse> childList;

    public CommentResponse(NoticeComment noticeComment) {
        this.id = noticeComment.getId();
        this.content = noticeComment.getContent();
        this.author = noticeComment.getAuthor();
        this.date = noticeComment.getDate();
        this.childList = noticeComment.getChildList().stream().map(e -> new ReplyResponse(e))
                .collect(Collectors.toList());
    }
}
