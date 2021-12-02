package com.example.marumaru_sparta_verspring.domain.profile;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity // 테이블과 연계됨을 스프링에게 알려줍니다.
public class Profile extends Timestamped { // 생성,수정 시간을 자동으로 만들어줍니다.

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long idx;

    @Column(nullable = false)
    private String dogImgUrl;

    @Column(nullable = false)
    private String dogName;

    @Column(nullable = false)
    private String dogAge;

    @Column(nullable = false)
    private String dogGender;

    @Column(nullable = false)
    private String dogComment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    //    public Profile(String dogName, String dogAge, String dogGender, String dogComment, String fileName) {
//        this.dogName = dogName;
//        this.dogAge = dogAge;
//        this.dogGender = dogGender;
//        this.dogComment = dogComment;
//        this.fileName = fileName;
//    }

//    public Profile(ProfileRequestDto requestDto) {
//        this.dogName = requestDto.getDogName();
//        this.dogAge = requestDto.getDogAge();
//        this.dogGender = requestDto.getDogGender();
//        this.dogComment = requestDto.getDogComment();
//        this.fileName = requestDto.getFileName();
//
//    }

//    public void update(ProfileRequestDto requestDto) {
//        this.dogName = requestDto.getDogName();
//        this.dogAge = requestDto.getDogAge();
//        this.dogGender = requestDto.getDogGender();
//        this.dogComment = requestDto.getDogComment();
//        this.fileName = requestDto.getFileName();
//    }
}