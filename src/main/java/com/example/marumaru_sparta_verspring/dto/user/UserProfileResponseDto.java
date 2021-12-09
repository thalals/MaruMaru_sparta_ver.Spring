package com.example.marumaru_sparta_verspring.dto.user;


import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileResponseDto {
    private long id;
    private String username;
    private String nickname;
    private String userProfileImg;
    private String userContent;
    private List<Profile> dogProfiles;
}
