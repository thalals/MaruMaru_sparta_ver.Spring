package com.example.marumaru_sparta_verspring.dto.articles;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long idx;    //idx
    private String title;
    private String content;
    private String createdAt;  //생성시간
    private String file;
    private String username;
    private int view;
}
