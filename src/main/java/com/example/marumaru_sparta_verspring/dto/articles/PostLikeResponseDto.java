package com.example.marumaru_sparta_verspring.dto.articles;

import com.example.marumaru_sparta_verspring.domain.articles.PostLike;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostLikeResponseDto {
    private Long idx;
    private List<PostLike> likes;
}
