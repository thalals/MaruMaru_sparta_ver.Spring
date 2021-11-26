package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.articles.Meet;
import com.example.marumaru_sparta_verspring.dto.MeetRequestDto;
import com.example.marumaru_sparta_verspring.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/meets")
    public Meet saveMeet(@RequestBody MeetRequestDto meetRequestDto) {
        return meetService.saveMeet(meetRequestDto);
    }


}
