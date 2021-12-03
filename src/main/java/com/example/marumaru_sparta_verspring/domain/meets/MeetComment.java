package com.example.marumaru_sparta_verspring.domain.meets;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
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

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="meet_idx", nullable = false)
    private Meet meet;

    public MeetComment(MeetCommentRequestDto meetCommentRequestDto, Meet meet) {
        this.comment = meetCommentRequestDto.getComment();
        this.meet = meet;
    }
}
