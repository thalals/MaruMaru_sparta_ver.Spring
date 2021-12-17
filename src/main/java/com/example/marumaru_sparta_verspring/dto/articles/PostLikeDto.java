package com.example.marumaru_sparta_verspring.dto.articles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeDto {
    private Long idx;
    private int like;
    private String status;
}
