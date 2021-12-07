package com.example.marumaru_sparta_verspring.domain.meets;

import com.example.marumaru_sparta_verspring.domain.Timestamped;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
public class Meet extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long idx;

    @Column
    private Long userId;

    @Column
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String imgUrl ="/img/no-pic.png";

    @Column(columnDefinition = "int default 0")
    private int view;

    @Column
    private String address;

    @Column
    private String date;

    @OneToMany(mappedBy = "meet", cascade = CascadeType.ALL)
    private List<MeetComment> comments;

    //조회수 증가
    public void upView(int view) {
        this.view = view;
    }
}
