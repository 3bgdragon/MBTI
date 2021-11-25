package com.example.security.controller;

import com.example.security.domain.board.Board;
import com.example.security.domain.board.BoardService;
import com.example.security.domain.board.dto.BoardResponse;
import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/mbti_board")
    public String mbtiBoard(Model model) {
        //그룹 = 시사,자유 등등...
        String mbti = userService.getUsermbti();
        List<BoardResponse> freeList = boardService.getFree(mbti);
        List<BoardResponse> qnaList = boardService.getQna(mbti);
        List<BoardResponse> communityList = boardService.getCommunity(mbti);

        model.addAttribute("mbti", mbti);

        model.addAttribute("freeList", freeList);
        model.addAttribute("freeSize", freeList.size());

        model.addAttribute("qnaList", qnaList);
        model.addAttribute("qnaSize", qnaList.size());

        model.addAttribute("communityList", communityList);
        model.addAttribute("communitySize", communityList.size());

        return "/user/board/mbti_board/mbti_board";
    }

    @GetMapping("/all_board")
    public String allMbtiboard(Model model) {
        List<BoardResponse> list = boardService.getAllBoard();
        model.addAttribute("boardList", list);

        return "/user/board/all_board/all_board";
    }
}
