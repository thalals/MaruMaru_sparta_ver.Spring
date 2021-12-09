package com.example.marumaru_sparta_verspring.controller;

import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.profile.ProfileResponseDto;
import com.example.marumaru_sparta_verspring.dto.user.*;
import com.example.marumaru_sparta_verspring.security.UserDetailsImpl;
import com.example.marumaru_sparta_verspring.service.UserService;
import com.example.marumaru_sparta_verspring.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final ModelMapper modelMapper;

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

    //회원 탈퇴
    @DeleteMapping("withdrawal/{username}")
    public void delPost(@PathVariable String username){
        userService.deleteUser(username);
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
    @PutMapping("/userprofile")
    public void updateUser(@Valid @RequestPart(value = "key") UserProfileRequestDto userProfileDto, @RequestPart(value = "userImage",required = false) MultipartFile userImage) throws IOException{
        userProfileDto.setUserProfileImg(userImage);
        userService.updateUser(userProfileDto);
    }

    //유저 정보 가져오기
    @GetMapping(value = "/userprofile/{username}")
    public UserProfileResponseDto getUserInfo(@PathVariable String username) {
        User user = userService.searchUser(username);
        UserProfileResponseDto userProfileResponseDto = modelMapper.map(user, UserProfileResponseDto.class);
        return userProfileResponseDto;
    }

    //나의 강아지 프로필 가져오기
    @GetMapping("/user/dogprofile")
    public List<ProfileResponseDto> getUserDogProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.dogProfiles(userDetails.getUser().getId());
    }

    //내가 쓴 게시물 가져오기
    @GetMapping(value = "/user/posts")
    public List<UserPostDto> getUserPosts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.post(userDetails.getUser());
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
