package com.example.security.controller;

import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final UserService userService;

    @GetMapping("/user/mbti_board")
    public String mbtiBoard(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/user/mbti_board";
    }

    @GetMapping("/user/all_mbti_board")
    public String allMbtiboard(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/user/all_mbti_board";
    }
}
