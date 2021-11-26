package com.example.marumaru_sparta_verspring.dto.articles;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String content;
    private String file;
}
