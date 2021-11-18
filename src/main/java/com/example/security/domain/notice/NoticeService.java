package com.example.security.domain.notice;

import com.example.security.domain.BaseService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService extends BaseService<Notice, Long> {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        super(noticeRepository);
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> gets() {
        BooleanBuilder builder = new BooleanBuilder();

        return findAll();
    }
}
