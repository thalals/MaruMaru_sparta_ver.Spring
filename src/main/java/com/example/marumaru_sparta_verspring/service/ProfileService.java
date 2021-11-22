package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.Profile;
import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;

    @Transactional
    public Long update(Long id, ProfileRequestDto requestDto) {
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        profile.update(requestDto);
        return profile.getID();
    }
}