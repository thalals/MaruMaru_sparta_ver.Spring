package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.S3Uploader;
import com.example.marumaru_sparta_verspring.domain.articles.Post;
import com.example.marumaru_sparta_verspring.domain.meets.Meet;
import com.example.marumaru_sparta_verspring.domain.meets.MeetComment;
import com.example.marumaru_sparta_verspring.domain.user.User;
import com.example.marumaru_sparta_verspring.dto.meets.MeetCommentRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetRequestDto;
import com.example.marumaru_sparta_verspring.dto.meets.MeetUpdateRequestDto;
import com.example.marumaru_sparta_verspring.repository.MeetCommentRepository;
import com.example.marumaru_sparta_verspring.repository.MeetRepository;
import com.example.marumaru_sparta_verspring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;
    private final S3Uploader s3Uploader;
    private final MeetCommentRepository meetCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Meet saveMeet(MeetRequestDto meetRequestDto, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        Meet meet = new Meet();
        meet.setUserId(userId);
        meet.setTitle(meetRequestDto.getTitle());
        meet.setUsername(user.getUsername());
        if (meetRequestDto.getImg() != null) {
            String imgUrl = s3Uploader.upload(meetRequestDto.getImg(), "static");
            meet.setImgUrl(imgUrl);
        }
        meet.setContent(meetRequestDto.getContent());
        meet.setAddress(meetRequestDto.getAddress());
        meet.setDate(meetRequestDto.getDate());
        meetRepository.save(meet);
        return meet;
    }

    public List<Meet> getMeets() {
        return meetRepository.findAll(Sort.by(Sort.Direction.DESC, "modifiedAt"));
    }


    public Meet getMeet(Long id) {
        Meet meet = meetRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        meet.setView(meet.getView()+1);
        meetRepository.save(meet);
        return meet;
    }

    @Transactional
    public MeetComment saveMeetComment(MeetCommentRequestDto meetCommentRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        Meet meet = meetRepository.findById(meetCommentRequestDto.getIdx()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        MeetComment meetComment = new MeetComment(meetCommentRequestDto, meet, user);
        meetCommentRepository.save(meetComment);
        return meetComment;
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Meet meet = meetRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));
        if (meet.getUserId() != userId) {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        } else {
            meetRepository.delete(meet);
        }
    }

    //게시글 수정시 본인 확인
    public boolean getMeetUserCheck(Long id, Long userID){
        Meet meet = meetRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        if(meet.getUserId()==userID){
            return true;
        }
        else return false;

    }

    @Transactional
    public Meet update(Long id, MeetUpdateRequestDto meetUpdateRequestDto, Long userId) {
        Meet meet = meetRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));

        if (meet.getUserId() != userId) {
            throw new IllegalArgumentException("작성자만 수정 가능");
        } else {
            meet.setTitle(meetUpdateRequestDto.getTitle());
            meet.setContent(meetUpdateRequestDto.getContent());
            meetRepository.save(meet);
            return meet;
        }
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        MeetComment meetComment = meetCommentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));
        if (meetComment.getUser().getId() != userId) {
            throw new IllegalArgumentException("작성자만 삭제 가능");
        } else {
            meetCommentRepository.deleteById(id);
        }
    }

    @Transactional
    public void updateComment(MeetCommentRequestDto meetCommentRequestDto, Long userId) {
        MeetComment meetComment = meetCommentRepository.findById(meetCommentRequestDto.getIdx()).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );

        if (meetComment.getUser().getId() != userId) {
            throw new IllegalArgumentException("작성자만 수정 가능");
        } else {
            meetComment.setComment(meetCommentRequestDto.getComment());
            meetCommentRepository.save(meetComment);
        }
    }
}
