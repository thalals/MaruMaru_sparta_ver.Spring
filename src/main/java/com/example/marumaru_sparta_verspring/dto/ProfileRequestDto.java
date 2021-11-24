package com.example.marumaru_sparta_verspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {
    private String dogName;
    private String dogAge;
    private String dogGender;
    private String dogComment;
    private String fileName;
}
