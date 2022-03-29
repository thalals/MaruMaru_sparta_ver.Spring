package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findAllByUser(User user);
    List<Post> findAllByTitleContainingIgnoreCase(String keyword);
    List<Post> findAllByContentContainingIgnoreCase(String keyword);
//    @Query(value = "select * from post p join user u where p.user_id = u.id",nativeQuery = true)
    @Query(value = "select p from Post p  join fetch p.user u", countQuery = "select count(p) from Post p")
    Page<Post> findWithPagination(Pageable pageable);

}
