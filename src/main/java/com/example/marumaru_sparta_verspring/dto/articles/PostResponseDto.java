package com.example.marumaru_sparta_verspring.dto.articles;

import com.example.marumaru_sparta_verspring.domain.articles.PostComment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private Long idx;    //idx
    private String title;
    private String content;
    private String createdAt;  //생성시간
    private String img;
    private String username;
    private int view;
    private List<PostComment> comments;
}
