package com.example.security.domain;

import com.example.security.core.BaseJpaQueryDSLRepository;
import com.example.security.core.BaseQueryDSLService;
import com.example.security.domain.board.QBoard;
import com.example.security.domain.board.like.QLike;
import com.example.security.domain.board.scrap.QScrap;
import com.example.security.domain.notice.QNotice;
import com.example.security.domain.notice.comment.QNoticeComment;
import com.example.security.domain.notice.comment.reply.QCommentReply;
import com.example.security.domain.user.QUserInfo;

import java.io.Serializable;

public class BaseService<T, ID extends Serializable> extends BaseQueryDSLService<T, ID> {

    /* 유저 */
    protected QUserInfo qUserInfo = QUserInfo.userInfo;

    /* 공지사항 */
    protected QNotice qNotice = QNotice.notice;
    protected QNoticeComment qNoticeComment = QNoticeComment.noticeComment;
    protected QCommentReply qCommentReply = QCommentReply.commentReply;

    /* 게시판 */
    protected QBoard qBoard = QBoard.board;
    protected QLike qLike = QLike.like;
    protected QScrap qScrap = QScrap.scrap;

    protected BaseJpaQueryDSLRepository<T, ID> repository;

    public BaseService(BaseJpaQueryDSLRepository<T, ID> repository) {
        super(repository);
        this.repository = repository;
    }
}
