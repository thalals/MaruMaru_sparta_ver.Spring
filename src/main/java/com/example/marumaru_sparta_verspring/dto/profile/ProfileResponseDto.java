package com.example.marumaru_sparta_verspring.dto.profile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    private Long idx;
    private String dogName;
    private String dogAge;
    private String dogGender;
    private String dogComment;
    private String createdAt;
}
