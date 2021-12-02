package com.example.marumaru_sparta_verspring.dto.profile;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    private Long idx;
    private String dogImg;
    private String dogName;
    private String dogAge;
    private String dogGender;
    private String dogComment;
    private Long userId;

    public ProfileResponseDto(Profile profile, Long userId) {
        this.idx = profile.getIdx();
        this.dogImg = profile.getDogImgUrl();
        this.dogName = profile.getDogName();
        this.dogAge = profile.getDogAge();
        this.dogGender = profile.getDogGender();
        this.dogComment = profile.getDogComment();
        this.userId = userId;
    }
}
