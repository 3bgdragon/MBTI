package com.example.security.domain.board.like;

import com.example.security.domain.BaseService;
import org.springframework.stereotype.Service;

@Service
public class LikeService extends BaseService<Like, Long> {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }
}
