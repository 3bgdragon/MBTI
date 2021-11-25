package com.example.security.domain.board;

import com.example.security.core.BaseTimeEntity;
import com.example.security.domain.board.like.Like;
import com.example.security.domain.board.scrap.Scrap;
import com.example.security.domain.user.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOARD")
@DynamicUpdate
@DynamicInsert
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", precision = 19, nullable = false)
    private Long id;

    @Column(name = "MBTI", length = 100, nullable = true)
    private String mbti;

    @Column(name = "GROUP", length = 20, nullable = false)
    private String group;

    @Column(name = "ALL", length = 1, nullable = false)
    private String all;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE")
    private UserInfo userInfo;

    @Column(name = "TITLE", length = 255, nullable = false)
    private String title;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "DATE", length = 20, nullable = false)
    private LocalDateTime date;

    @Column(name = "HIT", precision = 19, nullable = false)
    private Long hit;

    @Column(name = "IMPORTANT", length = 1, nullable = false)
    private String important;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    List<Like> likeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    List<Scrap> scrapList;

    @PrePersist
    public void prePersist() {
        this.hit = this.hit == null ? 0 : this.hit;
    }
}
