package com.example.security.domain.board;

import com.example.security.core.BaseJpaQueryDSLRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends BaseJpaQueryDSLRepository<Board, Long> {
}
