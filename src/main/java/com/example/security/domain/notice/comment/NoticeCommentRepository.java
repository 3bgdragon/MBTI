package com.example.security.domain.notice.comment;

import com.example.security.core.BaseJpaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeCommentRepository extends BaseJpaQueryDSLRepository<NoticeComment, Long> {
}
