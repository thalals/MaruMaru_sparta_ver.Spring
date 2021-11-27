package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostResponseDto;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import com.example.marumaru_sparta_verspring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postrepository;
    private final ModelMapper modelMapper;

    @PostMapping("/posts")
    public void CreatePosController(@RequestBody PostRequestDto postrequestdto){
        postService.CreatePost(postrequestdto);
    }

    @GetMapping("/post-list")
    public List<PostResponseDto> getPostList(){
        List<PostResponseDto> postList = postService.getPostList();
        return postList;
    }

    @GetMapping("/posts/detail")
    public PostResponseDto getPostDetail(@RequestParam Long id){
        Post post = postService.getPostDetail(id);
        PostResponseDto postResponseDto =  modelMapper.map(post,PostResponseDto.class);

        return postResponseDto;
    }

    @DeleteMapping("posts/detail")
    public void delPost(@RequestParam Long id){
        postService.DeletePost(id);
    }
}
