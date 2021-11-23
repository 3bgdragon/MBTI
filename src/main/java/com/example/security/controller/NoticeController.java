package com.example.security.controller;

import com.example.security.domain.notice.comment.NoticeCommentService;
import com.example.security.domain.notice.comment.dto.CommentRequest;
import com.example.security.domain.notice.comment.reply.CommentReplyService;
import com.example.security.domain.notice.comment.reply.dto.ReplyRequest;
import com.example.security.domain.notice.dto.NoticeRequest;
import com.example.security.domain.notice.dto.NoticeResponse;
import com.example.security.domain.notice.NoticeService;
import lombok.RequiredArgsConstructor;
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

    private final NoticeService noticeService;
    private final NoticeCommentService noticeCommentService;
    private final CommentReplyService commentReplyService;

    @GetMapping("/list")
    public String gets(String filter, @RequestParam(defaultValue = "1") int page, Model model) {
        Page<NoticeResponse> list = noticeService.gets(page, filter);
        model.addAttribute("notices", list);
        model.addAttribute("noticeElements", list.getTotalElements());
        model.addAttribute("number", list.getNumber());
        model.addAttribute("size", list.getSize());
        model.addAttribute("maxPage", 10);
        model.addAttribute("filter", filter);

        return "admin/notice/list";
    }

    @PostMapping("/list/paging")
    public String getPages(@RequestParam int page, Model model, @RequestBody String filter) {
        Page<NoticeResponse> list = noticeService.gets(page, filter);
        model.addAttribute("notices", list);
        model.addAttribute("noticeElements", list.getTotalElements());
        model.addAttribute("number", list.getNumber());
        model.addAttribute("size", list.getSize());
        model.addAttribute("page", page);
        model.addAttribute("maxPage", 10);

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
    public RedirectView comment(@RequestParam Long id, @RequestBody CommentRequest request, Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("commentList", noticeCommentService.saveComment(id, username, request));

        return new RedirectView("/admin/notice/detail/"+id);
    }

    @GetMapping("/comment/delete/{noticeId}/{id}")
    public RedirectView deleteComment(@PathVariable("noticeId") Long noticeId, @PathVariable("id") Long id) {
        noticeCommentService.deleteComment(id);

        return new RedirectView("/admin/notice/detail/"+noticeId);
    }

    @PostMapping("/comment/modify")
    public RedirectView modifyComment(@RequestBody CommentRequest request, Authentication authentication) {
        String username = authentication.getName();
        Long noticeId = noticeCommentService.modifyComment(request, username);

        return new RedirectView("/admin/notice/detail/"+noticeId);
    }

    @PostMapping("/comment/reply")
    public RedirectView reply(@RequestParam Long id, @RequestBody ReplyRequest request, Authentication authentication) {
        String username = authentication.getName();
        Long noticeId = commentReplyService.saveReply(request, id, username);

        return new RedirectView("/admin/notice/detail/"+noticeId);
    }

    @GetMapping("/comment/reply/delete/{id}")
    public RedirectView deleteReply(@PathVariable("id") Long replyId) {
        Long noticeId = commentReplyService.deleteReply(replyId);

        return new RedirectView("/admin/notice/detail/"+noticeId);
    }

    @PostMapping("/comment/reply/modify")
    public RedirectView modifyReply(@RequestBody ReplyRequest request, Authentication authentication) {
        String username = authentication.getName();
        Long noticeId = commentReplyService.modifyReply(request, username);

        return new RedirectView("/admin/notice/detail/"+noticeId);
    }
}
