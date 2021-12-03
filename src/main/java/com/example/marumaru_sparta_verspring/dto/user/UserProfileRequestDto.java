package com.example.marumaru_sparta_verspring.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;



@Getter
@Setter
public class UserProfileRequestDto {
    private long id;
    private String username;
    private String nickname;
    private MultipartFile userProfileImg;
    private String userContent;
}
