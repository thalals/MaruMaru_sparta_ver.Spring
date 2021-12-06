package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostCommentRequsetDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostCommentResponseDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostResponseDto;
import com.example.marumaru_sparta_verspring.repository.PostCommentRepository;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postrepository;
    private final UserRepository userRepository;
    private final PostCommentRepository postCommentRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;


    @Transactional
    public void createPost(@RequestBody PostRequestDto postRequestDto, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        Post post = new Post(postRequestDto, userId, user.getUsername());
        if(postRequestDto.getImg()!=null) {
            String imgUrl = s3Uploader.upload(postRequestDto.getImg(), "static");
            post.setImg(imgUrl);
        }
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

    @Transactional
    public void updatePost(PostRequestDto postRequestDto) throws IOException {
        Post post = postrepository.findById(postRequestDto.getIdx()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());

        if(postRequestDto.getImg()!=null){
            String imgUrl = s3Uploader.upload(postRequestDto.getImg(), "static");
            post.setImg(imgUrl);
        }

        postrepository.save(post);

    }

    @Transactional
    public void deletePost(Long id){
        postrepository.deleteById(id);
    }

    public List<PostResponseDto> getPostList(){
        List<Post> postList = postrepository.findAll();

        List<PostResponseDto> resultList = postList.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());

//        List<PostResponseDto> resultList = Arrays.asList(modelMapper.map(postList,PostResponseDto[].class));

        return resultList;
    }

    @Transactional
    public void createComment(@RequestBody PostCommentRequsetDto postCommentRequsetDto, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        Post post = postrepository.findById(postCommentRequsetDto.getPostid()).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );

        PostComment comment = new PostComment(postCommentRequsetDto, post, user);
        postCommentRepository.save(comment);
    }

    @Transactional
    public void deletePostComment(Long id){
        postCommentRepository.deleteById(id);
    }

    @Transactional
    public void updatePostComment(PostCommentRequsetDto postCommentRequsetDto){
        PostComment postComment = postCommentRepository.findById(postCommentRequsetDto.getCommentid()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        postComment.setComment(postCommentRequsetDto.getComment());
        postCommentRepository.save(postComment);
    }

}
