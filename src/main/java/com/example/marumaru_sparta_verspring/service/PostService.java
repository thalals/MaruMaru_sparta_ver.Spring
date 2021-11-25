package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.dto.PostRequestDto;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postrepository;

    public void CreatePost(@RequestBody PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postrepository.save(post);
    }
}
