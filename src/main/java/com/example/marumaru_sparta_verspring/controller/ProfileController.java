package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.Profile;
import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import com.example.marumaru_sparta_verspring.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    @PostMapping("/meets/profile") //프로필 작성
    public Profile setProfile(@RequestBody ProfileRequestDto profileRequestDto) {
        return profileService.setProfile(profileRequestDto);
    }

    @GetMapping("/meets/profiles") //프로필 리스트 불러오기
    public List<Profile> getProfiles() {
        return profileService.getProfiles();
    }

    @GetMapping("/meets/profile/{id}") //프로필 상세보기
    public Profile getProfile(@PathVariable Long id) { //변수 매핑
        return profileService.getProfile(id);
    }


    @PutMapping("/meets/profile/{id}") //프로필 수정
    public Long updateProfile(@PathVariable Long id, @RequestBody ProfileRequestDto requestDto) {
        profileService.update(id, requestDto);
        return id;
    }


    @DeleteMapping("/meets/profile/{id}")
    public Long deleteProfile(@PathVariable Long id) {
        profileRepository.deleteById(id);
        return id;
    }
}