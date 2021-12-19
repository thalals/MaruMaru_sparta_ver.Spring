package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.PostLike;
import com.example.marumaru_sparta_verspring.domain.user.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    //JPA 컨벤션 맞춰야 작동함
    PostLike findByPostAndUser(Post post, User user);
}
