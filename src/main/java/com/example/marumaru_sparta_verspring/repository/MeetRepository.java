package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.meets.Meet;
import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetRepository extends JpaRepository<Meet, Long> {
    List<Meet> findByUserId(Long userId);
}
