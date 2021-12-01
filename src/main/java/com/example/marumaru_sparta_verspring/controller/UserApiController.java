package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostCommentRequsetDto;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileRequestDto;
import com.example.marumaru_sparta_verspring.dto.user.*;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import com.example.marumaru_sparta_verspring.security.UserDetailsImpl;
import com.example.marumaru_sparta_verspring.service.UserService;
import com.example.marumaru_sparta_verspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserRepository userRepository;

    //로그인
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    //카카오로그인
    @PostMapping(value = "/login/kakao")
    public ResponseEntity<?> createAuthenticationTokenByKakao(@RequestBody SocialLoginDto socialLoginDto) throws Exception {
        String username = userService.kakaoLogin(socialLoginDto.getToken());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    //회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        userService.registerUser(userDto);
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    //회원가입 아이디 중복 체크
    @PostMapping("/signup/check-dup")
    public String checkUsername(@RequestBody UserDto userDto) {
        String exists;
        try {
            userService.checkExist(userDto);
        } catch (IllegalArgumentException e) {
            exists = "true";
            return exists;
        }
        exists = "FALSE";
        return exists;
    }
//
//    @PostMapping("/user/profile/check-nick")
//    public String checkNickname(@RequestBody UserDto userDto) {
//        String exists;
//        try {
//            userService.checkExist(userDto);
//        } catch (IllegalArgumentException e) {
//            exists = "true";
//            return exists;
//        }
//        exists =  "FALSE";
//        return exists;
//    }

    //유저프로필 수정
    @PutMapping("/userProfile")
    public void updateUser(@Valid @RequestPart(value = "key") UserProfileDto userProfileDto, @RequestPart(value = "userImage",required = false) MultipartFile userImage) throws IOException{
        userProfileDto.setUserImage(userImage);
        userService.updateUser(userProfileDto);
    }

    //유저 정보 가져오기
    @GetMapping(value = "/userProfile/{username}")
    public User getUserInfo(@PathVariable String username) {
        return userService.searchUser(username);
    }


    //회원 탈퇴
    @DeleteMapping("withdrawal/{username}")
    public void delPost(@PathVariable String username){
        userService.deleteUser(username);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
