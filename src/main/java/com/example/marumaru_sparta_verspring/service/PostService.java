package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostResponseDto;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postrepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;


    public void CreatePost(@RequestBody PostRequestDto postRequestDto, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        Post post = new Post(postRequestDto, userId, user.getUsername());
        String imgUrl = s3Uploader.upload(postRequestDto.getImg(), "static");
        post.setImg(imgUrl);
        postrepository.save(post);
    }

    public Post getPostDetail(Long id){
        Post post = postrepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
//        post.upView(post.getView()+1);
        post.setView(post.getView()+1);

        postrepository.save(post);
        return post;
    }

    public void DeletePost(Long id){
        postrepository.deleteById(id);
    }

    public List<PostResponseDto> getPostList(){
        List<Post> postList = postrepository.findAll();

        List<PostResponseDto> resultList = postList.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());

//        List<PostResponseDto> resultList = Arrays.asList(modelMapper.map(postList,PostResponseDto[].class));

        return resultList;
    }


}
