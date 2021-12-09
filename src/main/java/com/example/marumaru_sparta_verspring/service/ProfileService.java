package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final PostService profileService;
    private final ProfileRepository profileRepository;
    private final S3Uploader s3Uploader;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public Profile setProfile(ProfileRequestDto profileRequestDto, MultipartFile image, Long userId) throws IOException { //프로필 작성
        Profile profile = new Profile();
        String dogImageUrl = s3Uploader.upload(image, "static");
        Optional<User> userForId = userRepository.findById(userId);
        if (!userForId.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = userForId.get();

        profile.setIdx(profileRequestDto.getIdx());
        profile.setDogImgUrl(dogImageUrl);
        profile.setDogName(profileRequestDto.getDogName());
        profile.setDogAge(profileRequestDto.getDogAge());
        profile.setDogGender(profileRequestDto.getDogGender());
        profile.setDogComment(profileRequestDto.getDogComment());
        profile.setUser(user);

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

    public boolean getProfileUserCheck(Long profileid, Long userID) {
        Profile profile = profileRepository.findById(profileid).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        if (profile.getUser().getId() == userID) {
            return true;
        } else return false;
    }

    public void updateProfile(ProfileRequestDto profileRequestDto) throws IOException { //프로필 수정
        Profile profile = profileRepository.findById(profileRequestDto.getIdx()).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        profile.setDogName(profileRequestDto.getDogName());
        profile.setDogAge(profileRequestDto.getDogAge());
        profile.setDogGender(profileRequestDto.getDogGender());
        profile.setDogComment(profileRequestDto.getDogComment());

        if (profileRequestDto.getDogImg() != null) {
            String dogImgUrl = s3Uploader.upload(profileRequestDto.getDogImg(), "static");
            profile.setDogImgUrl(dogImgUrl);
        }
        profileRepository.save(profile);
    }

    @Transactional
    public String deleteProfile(Long id, Long userID) {
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 프로필이 존재하지 않습니다.")
        );

        if (profile.getUser().getId() == userID) {
            profileRepository.deleteById(id);
            return "success";
        } else {
            return "프로필 작성자가 아닙니다.";
        }
    }
}