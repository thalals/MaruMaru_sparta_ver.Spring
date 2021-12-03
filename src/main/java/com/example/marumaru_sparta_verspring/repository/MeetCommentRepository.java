package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.meets.MeetComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetCommentRepository extends JpaRepository<MeetComment, Long> {
}
