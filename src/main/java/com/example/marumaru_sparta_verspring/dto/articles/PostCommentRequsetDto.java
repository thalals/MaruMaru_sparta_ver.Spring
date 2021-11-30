package com.example.marumaru_sparta_verspring.dto.articles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCommentRequsetDto {
    private Long postid;
    private String comment;
}
