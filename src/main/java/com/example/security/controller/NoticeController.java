package com.example.security.controller;

import com.example.security.domain.notice.dto.NoticeRequest;
import com.example.security.domain.notice.dto.NoticeResponse;
import com.example.security.domain.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/list")
    public String gets(String filter, @RequestParam(defaultValue = "1") int page, Model model) {
        Page<NoticeResponse> list = noticeService.gets(page, filter);
        model.addAttribute("notices", list);
        model.addAttribute("contentSize", list.getContent().size());
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        model.addAttribute("filter", filter);

        return "admin/notice/list";
    }

    @PostMapping("/list/page")
    public String getPages(@RequestParam int page, @RequestBody String filter, Model model) {
        Page<NoticeResponse> list = noticeService.gets(page, filter);
        model.addAttribute("notices", list);
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);
        model.addAttribute("filter", filter);

        return "admin/notice/list :: notice-list-fragment";
    }

    @GetMapping("/regist")
    public String regist() {
        return "/admin/notice/regist";
    }

    @PostMapping("/save")
    public RedirectView save(NoticeRequest request, Authentication authentication) {
        String username = authentication.getName();
        noticeService.saveNotice(request, username);

        return new RedirectView("/admin/notice/list");
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long noticeId, Model model) {
        NoticeResponse notice = noticeService.findDetail(noticeId);
        model.addAttribute("notice", notice);

        return "admin/notice/detail";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long noticeId, Model model) {
        NoticeResponse notice = new NoticeResponse(noticeService.findById(noticeId).get());
        model.addAttribute("notice", notice);

        return "admin/notice/modify";
    }

    @PostMapping("/modify/save")
    public RedirectView modify(NoticeRequest request, Authentication authentication) {
        String username = authentication.getName();
        noticeService.modifyNotice(request, username);

        return new RedirectView("/admin/notice/detail/"+request.getNoticeId());
    }

    @PostMapping("/delete")
    public RedirectView delete(@RequestBody Long noticeId) {
        noticeService.deleteNotice(noticeId);

        return new RedirectView("/admin/notice/list");
    }

    @PostMapping("/comment")
    public String comment() {
        return null;
    }
}
