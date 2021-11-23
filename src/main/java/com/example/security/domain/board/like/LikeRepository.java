package com.example.security.domain.board.like;

import com.example.security.core.BaseJpaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends BaseJpaQueryDSLRepository<Like, Long> {
}
