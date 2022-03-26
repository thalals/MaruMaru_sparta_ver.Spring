package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Override
    @EntityGraph(attributePaths = {"post"})
    List<PostComment> findAll();
}
