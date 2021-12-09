package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostCommentRequsetDto;
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
        Post post = new Post(postRequestDto, user);
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
    
    //게시글 수정시 본인 확인
    public boolean getPostUserCheck(Long postid, Long userID){
        Post post = postrepository.findById(postid).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        if(post.getUser().getId()==userID){
            return true;
        }
        else return false;

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
    public String deletePost(Long id, Long userID){
        Post post = postrepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 게시물이 존재하지 않습니다.")
        );

        if(post.getUser().getId()==userID) {
            postrepository.deleteById(id);
            return "success";
        }
        else{
            return "게시글 작성자가 아닙니다.";
        }
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
    public String deletePostComment(Long id, Long userID){
        PostComment comment = postCommentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 댓글이 존재하지 않습니다.")
        );
        if(comment.getUser().getId()==userID) {
            postCommentRepository.deleteById(id);
            return "success";
        }
        else{
            return "댓글 작성자가 아닙니다.";
        }
    }

    @Transactional
    public String updatePostComment(PostCommentRequsetDto postCommentRequsetDto, Long userID){
        PostComment postComment = postCommentRepository.findById(postCommentRequsetDto.getCommentid()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        if(postComment.getUser().getId()==userID) {
            postComment.setComment(postCommentRequsetDto.getComment());
            postCommentRepository.save(postComment);
            return "success";
        }
        else{
            return "댓글 작성자가 아닙니다.";
        }

    }

}
