package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
}
