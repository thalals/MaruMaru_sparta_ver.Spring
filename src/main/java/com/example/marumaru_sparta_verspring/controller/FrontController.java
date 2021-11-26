package com.example.marumaru_sparta_verspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    @GetMapping("/meets")
    public String getPageMeetList(){
        return "articles/meet_list";
    }

    @GetMapping("/meet")
    public String getPageMeetPost(){
        return "articles/meet_upload";
    }

    @GetMapping("/meet/{id}")
    public String getPageMeet(){
        return "articles/meet_detail";
    }


    @GetMapping("/post")
    public String getPagePostList(){
        return "articles/post_list";
    }

    @GetMapping("/posts")
    public String getPagePosting(){
        return "articles/post_upload";
    }

    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }
}
