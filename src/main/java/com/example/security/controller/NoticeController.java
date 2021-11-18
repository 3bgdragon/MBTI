package com.example.security.controller;

import com.example.security.domain.notice.Notice;
import com.example.security.domain.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String gets(Model model) {
        List<Notice> list = noticeService.gets();
        model.addAttribute("notice-list", list);

        return "/admin/notice/list";
    }

    @GetMapping("/regist")
    public String regist() {
        return "/admin/notice/regist";
    }


}