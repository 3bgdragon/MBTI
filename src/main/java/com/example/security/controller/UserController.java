package com.example.security.controller;

import com.example.security.domain.user.UserInfoDto;
import com.example.security.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {
        userService.save(infoDto);
        return "redirect:/login";
    }

    @GetMapping("/")
    public String main(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/main";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String mbti = userService.getUsermbti();
        model.addAttribute("mbti", mbti);
        return "/user/dashboard";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return  "redirect:/login";
    }
}
