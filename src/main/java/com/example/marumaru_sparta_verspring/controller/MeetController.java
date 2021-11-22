package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.Meet;
import com.example.marumaru_sparta_verspring.dto.MeetRequestDto;
import com.example.marumaru_sparta_verspring.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/meets")
    public Meet saveMeet(@RequestBody MeetRequestDto meetRequestDto) {
        return meetService.saveMeet(meetRequestDto);
    }
}
