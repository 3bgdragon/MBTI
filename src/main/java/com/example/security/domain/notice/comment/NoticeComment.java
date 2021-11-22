package com.example.security.domain.notice.comment;

import com.example.security.core.BaseTimeEntity;
import com.example.security.domain.notice.Notice;
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
@Table(name = "NOTICE_COMMENT")
@DynamicUpdate
@DynamicInsert
public class NoticeComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", precision = 19, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Notice notice;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "AUTHOR", length = 100, nullable = false)
    private String author;

    @Column(name = "DATE", length = 20, nullable = false)
    private LocalDateTime date;
}
