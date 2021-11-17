package com.example.security.controller;

import com.example.security.domain.user.UserInfo;
import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @GetMapping("/denied")
    public String deniedUser (Model model) {
        return "/denied";
    }


    @GetMapping("/member/list")
    public String memberList(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/member/list";
    }

    @GetMapping("/member/modify")
    public String memberModify(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/member/modify";
    }

    @GetMapping("/member/regist")
    public String memberRegist(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/member/regist";
    }

    @ResponseBody
    @RequestMapping(value = "/member/getMemberEmail", method = RequestMethod.POST)
    public Boolean memberEmail(Model model,@RequestBody String email) {
        Optional<UserInfo> userInfo = userService.findUser(email);

        if(userInfo.isEmpty()) return true;
        else return false;
    }


    @GetMapping("/notice/list")
    public String noticeList(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/notice/list";
    }

    @GetMapping("/notice/regist")
    public String noticeRegist(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/notice/regist";
    }
}
