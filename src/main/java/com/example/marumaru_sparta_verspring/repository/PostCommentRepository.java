package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
