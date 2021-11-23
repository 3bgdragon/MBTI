package com.example.security.domain.notice.comment.reply;

import com.example.security.core.BaseJpaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends BaseJpaQueryDSLRepository<CommentReply, Long> {
}
