package com.example.security.controller;

import com.example.security.domain.user.UserInfo;
import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    @RequestMapping(value = "/member/list", method = RequestMethod.GET)
    public String memberList(String filter,Model model,@RequestParam(defaultValue = "1") int page) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);

        Page<UserInfo> users = userService.getAllUser(1,filter);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        model.addAttribute("filter", filter);
        return "/admin/member/list";
    }

    @RequestMapping(value = "/member/list/paging", method = RequestMethod.POST)
    public String memberListPaging(@RequestParam int page,@RequestBody String filter,Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);

        Page<UserInfo> users = userService.getAllUser(page,filter);
        model.addAttribute("users", users);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        model.addAttribute("filter", filter);
        return "admin/member/list :: userListFrag";
    }

    @GetMapping("/member/regist/{code}")
    public String memberModify(@PathVariable("code") String code,Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);

        Optional<UserInfo> user = userService.findUser(code);
        model.addAttribute("user", user.get());
        return "admin/member/modify";
    }

    @GetMapping(value = "/member/regist")
    public String memberRegist(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/admin/member/regist";
    }

    @ResponseBody
    @RequestMapping(value = "/member/getMemberEmail", method = RequestMethod.POST)
    public Boolean memberEmail(Model model,@RequestBody String email) {
        Optional<UserInfo> userInfo = userService.findUserByEmail(email);
        if(userInfo.isEmpty()) return true;
        else return false;
    }

}
