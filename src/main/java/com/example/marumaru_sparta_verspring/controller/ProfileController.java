package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import com.example.marumaru_sparta_verspring.security.UserDetailsImpl;
import com.example.marumaru_sparta_verspring.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final S3Uploader s3Uploader;

    @PostMapping("/profile") //프로필 작성
    public Profile setProfile(ProfileRequestDto profileRequestDto, @RequestPart(value = "dogImg") MultipartFile image,  @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getId();
        return profileService.setProfile(profileRequestDto, image, userId);
    }

    @GetMapping("/profile") //프로필 리스트 불러오기
    public List<Profile> getProfiles() throws IOException {
        return profileService.getProfiles();
    }

    @GetMapping("/profile/detail") //프로필 상세보기
    public Profile getProfile(@RequestParam Long id) { //변수 매핑
        return profileService.getProfile(id);
    }


    @PutMapping("/profile/detail") //프로필 수정
    public void updateProfile(@Valid @RequestPart(value = "key") ProfileRequestDto profileRequestDto, @RequestPart(value = "dogImg", required = false) MultipartFile dogImg) throws IOException {
        profileRequestDto.setDogImg(dogImg);
        profileService.updateProfile(profileRequestDto);
    }

    @GetMapping("/profile/check")
    public boolean checkUser(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUser().getId();
        return profileService.getProfileUserCheck(id, userId);
    }

    @DeleteMapping("/profile/detail") //프로필 삭제
    public String deleteProfile(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        return profileService.deleteProfile(id, userId);
    }
}