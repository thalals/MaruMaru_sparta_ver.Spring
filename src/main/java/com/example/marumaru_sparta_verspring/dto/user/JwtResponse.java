package com.example.marumaru_sparta_verspring.dto.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponse {
    private final String token;
    private final String username;
}
