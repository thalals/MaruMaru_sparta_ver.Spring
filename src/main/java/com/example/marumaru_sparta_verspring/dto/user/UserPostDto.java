package com.example.marumaru_sparta_verspring.dto.user;

import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class UserPostDto {
    private long idx;
    private String title;
    private User user;
    private String createdAt;  //생성시간

    public UserPostDto(Post post , User user){
        this.idx = post.getIdx();
        this.title = post.getTitle();
        this.user = user;
        this.createdAt = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}


