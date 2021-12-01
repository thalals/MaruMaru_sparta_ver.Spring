package com.example.marumaru_sparta_verspring.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;

@Getter
@Setter
public class MeetRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;
    private MultipartFile img;
    private String address;
    private String date;
}
