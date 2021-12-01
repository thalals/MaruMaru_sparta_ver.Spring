package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Meet;
import com.example.marumaru_sparta_verspring.dto.MeetRequestDto;
import com.example.marumaru_sparta_verspring.repository.MeetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final S3Uploader s3Uploader;

    public Meet saveMeet(MeetRequestDto meetRequestDto) throws IOException {
        Meet meet = new Meet();
        meet.setTitle(meetRequestDto.getTitle());
        meet.setContent(meetRequestDto.getContent());
        String imgUrl = s3Uploader.upload(meetRequestDto.getImg(), "static");
        meet.setImgUrl(imgUrl);
        meet.setAddress(meetRequestDto.getAddress());
        meet.setDate(meetRequestDto.getDate());

        meetRepository.save(meet);
        return meet;
    }

    public List<Meet> getMeet() {
        return meetRepository.findAll();
    }

}
