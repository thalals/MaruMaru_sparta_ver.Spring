package com.example.marumaru_sparta_verspring.domain.articles;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.articles.PostRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long idx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String img = "/img/no-pic.png";

    @Column(columnDefinition = "int default 0")
    private int view;

//    @Column(nullable = false)
//    private Long userId;
//
//    @Column(nullable = false)
//    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade=CascadeType.ALL)
    private List<PostComment> comments = new ArrayList<PostComment>();
    
    //새로운 게시글 생성
    public Post(PostRequestDto postRequestDto, User user){
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    //조회수 증가
    public void upView(int view){
        this.view = view;
    }

}
