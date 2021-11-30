package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.domain.user.UserRole;
import com.example.marumaru_sparta_verspring.dto.user.SignupRequestDto;
import com.example.marumaru_sparta_verspring.dto.user.UserDto;
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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        // 패스워드 인코딩
        String password = passwordEncoder.encode(requestDto.getPassword());

        UserRole role = UserRole.USER;

        User user = new User(username, password, role);
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

}