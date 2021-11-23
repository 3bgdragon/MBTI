package com.example.security.controller;

import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final UserService userService;


    @GetMapping("/mbti_board")
    public String mbtiBoard(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);

        return "/user/mbti_board";
    }

    @GetMapping("/all_mbti_board")
    public String allMbtiboard(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/user/all_mbti_board";
    }
}
