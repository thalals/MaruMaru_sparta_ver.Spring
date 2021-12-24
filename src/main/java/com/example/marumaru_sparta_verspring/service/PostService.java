package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import com.example.marumaru_sparta_verspring.domain.articles.PostLike;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.*;
import com.example.marumaru_sparta_verspring.repository.PostCommentRepository;
import com.example.marumaru_sparta_verspring.repository.PostLikeRepository;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PostLikeRepository postLikeRepository;
    private final ModelMapper modelMapper;
    private final S3Uploader s3Uploader;    //service 로 change
    private final AwsService awsService;


    @Transactional
    public void createPost(@RequestBody PostRequestDto postRequestDto, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        Post post = new Post(postRequestDto, user);
        if(postRequestDto.getImg()!=null) {
//            String imgUrl = s3Uploader.upload(postRequestDto.getImg(), "static");
            String imgUrl = awsService.upload(postRequestDto.getImg());
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

    //게시글 리스트
    public Page<Post> getPostList(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> postList = postrepository.findAll(pageable);

        return postList;
    }

    //게시글 검색
    public List<Post> getPostSearchList(String category, String keyword){
        if(category.equals("user")){
            User user = userRepository.findByUsername(keyword).orElseThrow(
                    () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
            );

            return postrepository.findAllByUser(user);
        }
        else if(category.equals("title")){
            return postrepository.findAllByTitleContainingIgnoreCase(keyword);
        }
        else if(category.equals("content")){
            return postrepository.findAllByContentContainingIgnoreCase(keyword);
        }
        return postrepository.findAll();
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

    //좋아요 service
    @Transactional
    public Post setPostLike(PostLikeDto postLikeDto, Long userID){
        User user = userRepository.findById(userID).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        Post post = postrepository.findById(postLikeDto.getIdx()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        
        if(postLikeDto.getStatus().equals("up")){
            PostLike postLike = new PostLike(user, post);
            postLikeRepository.save(postLike);
        }
        else if(postLikeDto.getStatus().equals("down")){
            PostLike postLike = postLikeRepository.findByPostAndUser(post,user);
            postLikeRepository.deleteById(postLike.getIdx());
        }

        return post;
    }

    //게시글 좋아요 리스트
    @Transactional
    public boolean checkUserLike(Long postId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        Post post = postrepository.findById(postId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        PostLike postLike = postLikeRepository.findByPostAndUser(post,user);

        //좋아요 누른 적 없음
        if(postLike==null)
            return false;
        //있음
        return true;
    }
}
