package com.example.security.domain.notice;

import com.example.security.core.BaseJpaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends BaseJpaQueryDSLRepository<Notice, Long> {
    Notice findByNoticeId(Long noticeId);
}
