package com.example.marumaru_sparta_verspring.domain.meets;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.meets.MeetCommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class MeetComment extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long idx;

    @Column(nullable = false)
    private String comment;

    @JsonIgnore // 응답 필드에서 숨기기
    @ManyToOne
    @JoinColumn(name="meet_idx", nullable = false)
    private Meet meet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MeetComment(MeetCommentRequestDto meetCommentRequestDto, Meet meet, User user) {
        this.comment = meetCommentRequestDto.getComment();
        this.meet = meet;
        this.user = user;
    }
}
