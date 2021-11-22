package com.example.security.domain.notice;

import com.example.security.domain.BaseService;
import com.example.security.domain.notice.dto.NoticeHitRequest;
import com.example.security.domain.notice.dto.NoticeRequest;
import com.example.security.domain.notice.dto.NoticeResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService extends BaseService<Notice, Long> {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        super(noticeRepository);
        this.noticeRepository = noticeRepository;
    }

    public Page<NoticeResponse> gets(int page, String filter) {
        Pageable pageable = PageRequest.of(page-1, 10);
        BooleanBuilder builder = new BooleanBuilder();

        if(isNotEmpty(filter)) {
            if(filter.equals(" ")){
                filter = null;
            }
        }

        if (isNotEmpty(filter)) {
            builder.and(qNotice.title.contains(filter));
            builder.or(qNotice.author.contains(filter));
        }

        QueryResults results = select().from(qNotice)
                .where(builder).
                orderBy(qNotice.important.desc(), qNotice.date.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Transactional
    public void saveNotice(NoticeRequest request, String username) {
        request.setAuthor(username);

        if (request.getImportant() == null) {
            request.setImportant("N");
        }

        save(request.toEntity());
    }

    public NoticeResponse findDetail(Long noticeId) {
        Notice notice = noticeRepository.findByNoticeId(noticeId);
        NoticeResponse response = new NoticeResponse(notice);
        plusHit(response);

        return response;
    }

    @Transactional
    public void plusHit(NoticeResponse notice) {
        notice.setHit(notice.getHit()+1);

        save(new NoticeHitRequest().toEntity(notice));
    }

    @Transactional
    public void modifyNotice(NoticeRequest request, String username) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qNotice.noticeId.eq(request.getNoticeId()));

        Notice notice = noticeRepository.findByNoticeId(request.getNoticeId());
        NoticeResponse response = new NoticeResponse(notice);
        request.setAuthor(username);
        request.setHit(response.getHit());

        if (request.getImportant() == null) {
            request.setImportant("N");
        }

        save(request.toEntity());
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        if (isNotEmpty(String.valueOf(noticeId))) {
            noticeRepository.deleteById(noticeId);
        }
    }
}
