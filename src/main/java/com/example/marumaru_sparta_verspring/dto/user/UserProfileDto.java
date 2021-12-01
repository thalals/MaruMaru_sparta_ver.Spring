package com.example.marumaru_sparta_verspring.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserProfileDto {
    private long id;
    private String username;
    private String nickname;
    private MultipartFile userImage;
    private String userContent;
}
