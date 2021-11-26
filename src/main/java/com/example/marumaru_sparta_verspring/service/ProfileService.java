package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.articles.Profile;
import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile setProfile(ProfileRequestDto profileRequestDto) { //프로필 작성
        Profile profile = new Profile(profileRequestDto);
        profileRepository.save(profile);
        return profile;
    }

    public Profile getProfile(Long id){ //프로필 상세보기
        return profileRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }

    public List<Profile> getProfiles() { //프로필 리스트 보기
        return profileRepository.findAll();
    }

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Long update(Long id, ProfileRequestDto profileRequestDto) { //프로필 수정
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        profile.update(profileRequestDto);
        return profile.getIdx();
    }
}