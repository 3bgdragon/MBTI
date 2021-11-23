package com.example.security.domain.notice.comment.reply;

import com.example.security.domain.BaseService;
import com.example.security.domain.notice.comment.NoticeComment;
import com.example.security.domain.notice.comment.NoticeCommentRepository;
import com.example.security.domain.notice.comment.reply.dto.ReplyRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentReplyService extends BaseService<CommentReply, Long> {

    private final CommentReplyRepository commentReplyRepository;
    private final NoticeCommentRepository noticeCommentRepository;

    public CommentReplyService(CommentReplyRepository commentReplyRepository, NoticeCommentRepository noticeCommentRepository) {
        super(commentReplyRepository);
        this.commentReplyRepository = commentReplyRepository;
        this.noticeCommentRepository = noticeCommentRepository;
    }

    @Transactional
    public Long saveReply(ReplyRequest request, Long id, String username) {
        NoticeComment noticeComment = noticeCommentRepository.findById(id).get();

        request.setAuthor(username);
        request.setDate(LocalDateTime.now());
        request.setNoticeComment(noticeComment);

        return save(request.toEntity()).getNoticeComment().getNotice().getNoticeId();
    }

    @Transactional
    public Long deleteReply(Long replyId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCommentReply.id.eq(replyId));
        CommentReply commentReply = select().from(qCommentReply).where(builder).fetchOne();

        NoticeComment comment = new NoticeComment();
        if (commentReply != null) {
            comment= commentReply.getNoticeComment();
        }

        delete(commentReply);

        return comment.getNotice().getNoticeId();
    }

    @Transactional
    public Long modifyReply(ReplyRequest request, String username) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qCommentReply.id.eq(request.getId()));
        CommentReply reply = findById(request.getId()).get();

        request.setAuthor(username);
        request.setNoticeComment(reply.getNoticeComment());
        request.setDate(LocalDateTime.now());

        return save(request.toEntity()).getNoticeComment().getNotice().getNoticeId();
    }
}
