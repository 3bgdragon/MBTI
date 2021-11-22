package com.example.security.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatController {

    @GetMapping("/chatPage")
    public String chatPage() {
        return "/user/chat";
    }

    @GetMapping("/chat")
    public void chatGET(){
        log.info("@ChatController, chat Get()");
    }



}
