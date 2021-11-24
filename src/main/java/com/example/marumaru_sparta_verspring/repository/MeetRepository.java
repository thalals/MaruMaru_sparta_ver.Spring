package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.posts.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, Long> {
}
