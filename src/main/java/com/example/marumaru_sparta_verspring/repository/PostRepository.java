package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findAllByUser(User user);
    List<Post> findAllByTitleContainingIgnoreCase(String keyword);
    List<Post> findAllByContentContainingIgnoreCase(String keyword);
}
