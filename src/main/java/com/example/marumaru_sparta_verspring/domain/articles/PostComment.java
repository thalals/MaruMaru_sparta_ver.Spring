package com.example.marumaru_sparta_verspring.domain.articles;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostCommentRequsetDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostComment(PostCommentRequsetDto postCommentRequsetDto, Post post, User user){
        this.post = post;
        this.user = user;
        this.comment = postCommentRequsetDto.getComment();

    }
}
