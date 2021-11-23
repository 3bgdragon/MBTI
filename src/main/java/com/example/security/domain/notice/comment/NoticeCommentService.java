package com.example.security.domain.notice.comment;

import com.example.security.domain.BaseService;
import com.example.security.domain.notice.Notice;
import com.example.security.domain.notice.NoticeRepository;
import com.example.security.domain.notice.comment.dto.CommentRequest;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeCommentService extends BaseService<NoticeComment, Long> {

    private final NoticeCommentRepository noticeCommentRepository;
    private final NoticeRepository noticeRepository;

    public NoticeCommentService(NoticeCommentRepository noticeCommentRepository, NoticeRepository noticeRepository) {
        super(noticeCommentRepository);
        this.noticeCommentRepository = noticeCommentRepository;
        this.noticeRepository = noticeRepository;
    }

    @Transactional
    public List<NoticeComment> saveComment(Long noticeId, String username, CommentRequest request) {
        Notice notice = noticeRepository.findByNoticeId(noticeId);

        request.setDate(LocalDateTime.now());
        request.setNotice(notice);
        request.setAuthor(username);
        save(request.toEntity());

        List<NoticeComment> list = select().from(qNotice).fetch();

        return list;
    }

    @Transactional
    public void deleteComment(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNoticeComment.id.eq(id));
        NoticeComment comment = select().from(qNoticeComment).where(builder).fetchOne();

        delete(comment);
    }

    @Transactional
    public Long modifyComment(CommentRequest request, String username) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNoticeComment.id.eq(request.getId()));
        NoticeComment comment = findById(request.getId()).get();

        request.setAuthor(username);
        request.setNotice(comment.getNotice());
        request.setDate(LocalDateTime.now());

        return save(request.toEntity()).getNotice().getNoticeId();
    }
}
