package com.example.security.domain.notice.comment.reply;

import com.example.security.core.BaseTimeEntity;
import com.example.security.domain.notice.comment.NoticeComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMENT_REPLY")
@DynamicInsert
@DynamicUpdate
public class CommentReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", precision = 19, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private NoticeComment noticeComment;

    @Column(name = "AUTHOR", length = 100, nullable = false)
    private String author;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "DATE", length = 20, nullable = false)
    private LocalDateTime date;
}
