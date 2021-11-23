package com.example.security.domain.board;

import com.example.security.domain.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BoardService extends BaseService<Board, Long> {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        super(boardRepository);
        this.boardRepository = boardRepository;
    }
}
