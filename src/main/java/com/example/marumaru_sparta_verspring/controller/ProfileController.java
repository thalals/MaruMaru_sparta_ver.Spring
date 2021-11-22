package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.Profile;
import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import com.example.marumaru_sparta_verspring.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Controller
public class ProfileController {

    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @RequestMapping(value = "/meets/profile")
    public String upload() {
        return "profile_upload";
    }


    @PostMapping("/meets/profile")
    public Profile createProfile(@RequestBody ProfileRequestDto requestDto) {
        Profile profile = new Profile(requestDto);
        return profileRepository.save(profile);
    }

    @PutMapping("/meets/profile/{id}")
    public Long updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDto requestDto) {
        profileService.update(id, requestDto);
        return id;
    }

    @GetMapping("/meets/profile")
    public List<Profile> getProfiles() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        return profileRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(start, end);
    }

    @DeleteMapping("/meets/profile/{id}")
    public Long deleteProfile(@PathVariable Long id) {
        profileRepository.deleteById(id);
        return id;
    }
}