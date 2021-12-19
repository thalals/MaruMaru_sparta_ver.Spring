package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.meets.Meet;
import com.example.marumaru_sparta_verspring.domain.meets.MeetComment;
import com.example.marumaru_sparta_verspring.dto.meets.MeetCommentRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetUpdateRequestDto;
import com.example.marumaru_sparta_verspring.security.UserDetailsImpl;
import com.example.marumaru_sparta_verspring.service.MeetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MeetController {

    private final MeetService meetService;

    @PostMapping("/meets")
    public Meet saveMeet(@Valid @ModelAttribute MeetRequestDto meetRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        return meetService.saveMeet(meetRequestDto, userId);
    }

    @GetMapping("/meets")
    public List<Meet> showMeets() throws IOException {
        return meetService.getMeets();
    }

    @GetMapping("/meet/{id}")
    public Meet showMeet(@PathVariable Long id) throws IOException {
        return meetService.getMeet(id);
    }

    @PutMapping("/meet/{id}")
    public Meet update(@PathVariable Long id, @RequestBody MeetUpdateRequestDto meetUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return meetService.update(id, meetUpdateRequestDto, userId);
    }

    @DeleteMapping("/meet/{id}")
    public Long deleteMeet(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        meetService.delete(id, userId);
        return id;
    }

    @PostMapping("/meet/comment")
    public MeetComment setMeetComment(@RequestBody MeetCommentRequestDto meetCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return meetService.saveMeetComment(meetCommentRequestDto, userId);
    }

    @DeleteMapping("/meet/comment/{id}")
    public void deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        meetService.deleteComment(id, userId);
    }


    @PutMapping("/meet/comment")
    public void updateComment(@RequestBody MeetCommentRequestDto meetCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        meetService.updateComment(meetCommentRequestDto, userId);
    }
}
