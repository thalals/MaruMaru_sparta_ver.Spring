package com.example.marumaru_sparta_verspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    @GetMapping("/meets")
    public String getPageIndex(){
        return "meet_list";
    }

    @GetMapping("/meet")
    public String getPageView(){
        return "meet_upload";
    }

    @GetMapping("/detail")
    public String getPageDetail(){
        return "meet_detail";
    }

}
