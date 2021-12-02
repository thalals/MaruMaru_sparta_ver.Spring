package com.example.marumaru_sparta_verspring.dto.user;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UserProfileDto {
    private long id;
    private String username;
    private String nickname;
    private MultipartFile userImage;
    private String userContent;
    private List<Profile> dogProfiles;
}
