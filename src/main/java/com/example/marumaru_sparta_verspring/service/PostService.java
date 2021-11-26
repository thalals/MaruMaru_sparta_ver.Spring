package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostResponseDto;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    @Autowired
    private final PostRepository postrepository;
    private final ModelMapper modelMapper;

    public void CreatePost(@RequestBody PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postrepository.save(post);
    }

    public List<PostResponseDto> getPostList(){
        List<Post> postList = postrepository.findAll();
        if(postList.size()>0)
            System.out.println("1번"+postList.get(0).getTitle());
//        List<PostResponseDto> resultList = postList.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());
        List<PostResponseDto> resultList = Arrays.asList(modelMapper.map(postList,PostResponseDto[].class));
        if(resultList.size()>0)
            System.out.println(resultList.get(0).getTitle());
        return resultList;
    }
}
