package com.example.security.domain.notice;

import com.example.security.core.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NOTICE")
@DynamicUpdate
@DynamicInsert
public class Notice extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID", precision = 19, nullable = false)
    private Long noticeId;

    @Column(name = "AUTHOR", length = 100, nullable = false)
    private String author;

    @Column(name = "TITLE", length = 255, nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "IMPORTANT", length = 1, nullable = true)
    private String important;
}
