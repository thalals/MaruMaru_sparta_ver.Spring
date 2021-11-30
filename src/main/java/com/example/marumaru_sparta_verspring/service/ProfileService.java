package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileResponseDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileService {

    @Autowired
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

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

    public List<ProfileResponseDto> getProfiles(){
        List<Profile> profileList = profileRepository.findAll();


        List<ProfileResponseDto> resultList = profileList.stream().map(profile -> modelMapper.map(profile, ProfileResponseDto.class)).collect(Collectors.toList());
//        List<ProfileResponseDto> resultList = Arrays.asList(modelMapper.map(profileList,ProfileResponseDto[].class));

        return resultList;
    }

//    public List<Profile> getProfiles() { //프로필 리스트 보기
//        return profileRepository.findAll();
//    }

    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
    public Long update(Long id, ProfileRequestDto profileRequestDto) { //프로필 수정
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        profile.update(profileRequestDto);
        return profile.getIdx();
    }
}