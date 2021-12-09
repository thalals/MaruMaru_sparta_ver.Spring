package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.domain.user.UserRole;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileResponseDto;
import com.example.marumaru_sparta_verspring.dto.user.SignupRequestDto;
import com.example.marumaru_sparta_verspring.dto.user.UserDto;
import com.example.marumaru_sparta_verspring.dto.user.UserProfileRequestDto;
import com.example.marumaru_sparta_verspring.dto.user.UserPostDto;
import com.example.marumaru_sparta_verspring.repository.PostRepository;
import com.example.marumaru_sparta_verspring.repository.ProfileRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import com.example.marumaru_sparta_verspring.security.kakao.KakaoOAuth2;
import com.example.marumaru_sparta_verspring.security.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
    private final S3Uploader s3Uploader;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = username;

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        UserRole role = UserRole.USER;


        User user = new User(username, password, role, nickname);
        userRepository.save(user);

        return user;
    }

    public String kakaoLogin(String token) {
        // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(token);
        Long kakaoId = userInfo.getId();
        String nickname = userInfo.getNickname();

        // 우리 DB 에서 회원 Id 와 패스워드
        // 회원 Id = 카카오 nickname
        String username = nickname;
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + ADMIN_TOKEN;

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);

        // 카카오 정보로 회원가입
        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);
            UserRole role = UserRole.USER;

            kakaoUser = new User(nickname, encodedPassword, kakaoId, role);
            userRepository.save(kakaoUser);
        }

        // 로그인 처리
        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return username;
    }


    public void checkExist(UserDto userDto) {
        String username = userDto.getUsername();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
    }

    public User searchUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        userRepository.delete(user);
    }

    @Transactional
    public void updateUser(UserProfileRequestDto userProfileDto) throws IOException {
        User user = userRepository.findByUsername(userProfileDto.getUsername()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        user.setUsername(userProfileDto.getUsername());
        user.setUserContent(userProfileDto.getUserContent());
        user.setNickname(userProfileDto.getNickname());


        if (userProfileDto.getUserProfileImg() != null) {
            String userProfileImg = s3Uploader.upload(userProfileDto.getUserProfileImg(), "static");
            user.setUserProfileImg(userProfileImg);
        }

        userRepository.save(user);

    }

    public  List<ProfileResponseDto> dogProfiles(Long userid) {
        List<ProfileResponseDto> profileResponsetDtoList = new ArrayList<>();
        List<Profile> dogProfiles = profileRepository.findByUserId(userid);
        for (Profile getProfile : dogProfiles) {
            ProfileResponseDto profileResponseDto = new ProfileResponseDto(getProfile, userid);
            profileResponsetDtoList.add(profileResponseDto);
        }
        return profileResponsetDtoList;
    }


    public List<UserPostDto> post(User user) {
        List<UserPostDto> userPostDtoList = new ArrayList<>();
        List<Post> posts = postRepository.findByUserId(user.getId());
        for (Post getPost : posts) {
            UserPostDto userPostDto = new UserPostDto(getPost, user);
            userPostDtoList.add(userPostDto);
        }
        return userPostDtoList;
    }
}