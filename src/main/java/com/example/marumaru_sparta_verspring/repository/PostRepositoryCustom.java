package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> findByCustom_offsetPaging(Pageable pageable);
    Page<Post> findByCustom_cursorPaging(Pageable pageable, String sorting, Long idx);

}
