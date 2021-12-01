package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final PostService profileService;
    private final ProfileRepository profileRepository;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;


    public Profile setProfile(ProfileRequestDto profileRequestDto, MultipartFile image) throws IOException { //프로필 작성
        Profile profile = new Profile();
        String dogImageUrl = s3Uploader.upload(image ,"static");
        profile.setIdx(profileRequestDto.getIdx());
        profile.setDogImgUrl(dogImageUrl);
        profile.setDogName(profileRequestDto.getDogName());
        profile.setDogAge(profileRequestDto.getDogAge());
        profile.setDogGender(profileRequestDto.getDogGender());
        profile.setDogComment(profileRequestDto.getDogComment());

        profileRepository.save(profile);
        return profile;
    }

    public List<Profile> getProfiles() { //프로필 리스트
        return profileRepository.findAll();
    }

    public Profile getProfile(Long id) { //프로필 상세보기
        return profileRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }
//
//

//
//    @Transactional // SQL 쿼리가 일어나야 함을 스프링에게 알려줌
//    public Long update(Long id, ProfileRequestDto profileRequestDto) { //프로필 수정
//        Profile profile = profileRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        profile.update(profileRequestDto);
//        return profile.getIdx();
//    }
}