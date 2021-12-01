package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Meet;
import com.example.marumaru_sparta_verspring.dto.MeetRequestDto;
import com.example.marumaru_sparta_verspring.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/meet/api")
public class MeetController {

    private final MeetService meetService;
    private final S3Uploader s3Uploader;

    @PostMapping("/meets")
    public Meet saveMeet(@Valid @ModelAttribute MeetRequestDto meetRequestDto) throws IOException {
        return meetService.saveMeet(meetRequestDto);
    }

    @GetMapping("/meets")
    public List<Meet> setMeet() throws IOException {
        return meetService.getMeet();
    }

    @PostMapping("/images") // 쿼리스크트 - 폼데이터
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static");
    }
}
