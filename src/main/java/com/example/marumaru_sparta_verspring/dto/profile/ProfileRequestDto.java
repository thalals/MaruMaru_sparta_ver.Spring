package com.example.marumaru_sparta_verspring.dto.profile;

import com.example.marumaru_sparta_verspring.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProfileRequestDto {
    private Long idx;
    private MultipartFile dogImg;
    @NotBlank(message = "강아지 이름을 입력해주세요!")
    private String dogName;
    @NotBlank(message = "강아지 나이를 입력해주세요!")
    private String dogAge;
    @NotBlank(message = "강아지 성별을 입력해주세요!")
    private String dogGender;
    @NotBlank(message = "강아지 소개를 입력해주세요!")
    private String dogComment;
    private Long userId;
}
