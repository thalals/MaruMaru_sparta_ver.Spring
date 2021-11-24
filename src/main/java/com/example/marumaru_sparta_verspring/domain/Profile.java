package com.example.marumaru_sparta_verspring.domain;

import com.example.marumaru_sparta_verspring.dto.ProfileRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor // 기본생성자를 만듭니다.
@Getter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Profile extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long ID;

    @Column(nullable = false)
    private String dogName;

    @Column(nullable = false)
    private int dogAge;

    @Column(nullable = false)
    private String dogGender;

    @Column(nullable = false)
    private String dogComment;

    @Column(nullable = false)
    private String file;

    public Profile(String dogName, int dogAge, String dogGender, String dogComment, String file) {
        this.dogName = dogName;
        this.dogAge = dogAge;
        this.dogGender = dogGender;
        this.dogComment = dogComment;
        this.file = file;
    }

    public Profile(ProfileRequestDto requestDto) {
        this.dogName = requestDto.getDogName();
        this.dogAge = requestDto.getDogAge();
        this.dogGender = requestDto.getDogGender();
        this.dogComment = requestDto.getDogComment();
        this.file = requestDto.getFile();

    }

    public void update(ProfileRequestDto requestDto) {
        this.dogName = requestDto.getDogName();
        this.dogAge = requestDto.getDogAge();
        this.dogGender = requestDto.getDogGender();
        this.dogComment = requestDto.getDogComment();
        this.file = requestDto.getFile();
    }
}