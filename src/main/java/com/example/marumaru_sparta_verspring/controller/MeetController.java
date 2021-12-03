package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.meets.Meet;
import com.example.marumaru_sparta_verspring.dto.meets.MeetCommentRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetUpdateRequestDto;
import com.example.marumaru_sparta_verspring.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<Meet> showMeets() throws IOException {
        return meetService.getMeets();
    }

    @GetMapping("/meet/{id}")
    public Meet showMeet(@PathVariable Long id) throws IOException {
        return meetService.getMeet(id);
    }

    @PostMapping("/meet/comment")
    public void  setMeetComment(@RequestBody MeetCommentRequestDto meetCommentRequestDto) {
        meetService.saveMeetComment(meetCommentRequestDto);
    }

    @PutMapping("/meet/{id}")
    public Long update(@PathVariable Long id, @RequestBody MeetUpdateRequestDto meetUpdateRequestDto) {
        meetService.update(id, meetUpdateRequestDto);
        return id;
    }

    @DeleteMapping("/meet/{id}")
    public Long deleteMeet(@PathVariable Long id) throws IOException {
        meetService.delete(id);
        return id;
    }
}
