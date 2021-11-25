package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.dto.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.service.PostService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void CreatePosController(@RequestBody PostRequestDto postrequestdto){
        postService.CreatePost(postrequestdto);
    }
    
}
