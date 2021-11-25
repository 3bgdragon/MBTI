package com.example.security.domain.board;

import com.example.security.domain.BaseService;
import com.example.security.domain.board.dto.BoardResponse;
import com.querydsl.core.BooleanBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService extends BaseService<Board, Long> {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        super(boardRepository);
        this.boardRepository = boardRepository;
    }

    public List<BoardResponse> getFree(String mbti) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBoard.mbti.eq(mbti));
        builder.and(qBoard.all.eq("N"));
        builder.and(qBoard.group.eq("FREE"));

        List<Board> boardList = select()
                .from(qBoard)
                .where(builder)
                .orderBy(qBoard.hit.desc())
                .limit(5)
                .fetch();

        return boardList.stream().map(e -> new BoardResponse(e))
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getQna(String mbti) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBoard.mbti.eq(mbti));
        builder.and(qBoard.all.eq("N"));
        builder.and(qBoard.group.eq("QNA"));

        List<Board> boardList = select()
                .from(qBoard)
                .where(builder)
                .orderBy(qBoard.hit.desc()).fetch();

        return boardList.stream().map(e -> new BoardResponse(e))
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getCommunity(String mbti) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBoard.mbti.eq(mbti));
        builder.and(qBoard.all.eq("N"));
        builder.and(qBoard.group.eq("COMMUNITY"));

        List<Board> boardList = select()
                .from(qBoard)
                .where(builder)
                .orderBy(qBoard.hit.desc()).fetch();

        return boardList.stream().map(e -> new BoardResponse(e))
                .collect(Collectors.toList());
    }

    public List<BoardResponse> getAllBoard() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qBoard.all.eq("Y"));

        List<Board> boardList = select().from(qBoard).where(builder).fetch();

        return boardList.stream().map(e -> new BoardResponse(e))
                .collect(Collectors.toList());
    }
}
