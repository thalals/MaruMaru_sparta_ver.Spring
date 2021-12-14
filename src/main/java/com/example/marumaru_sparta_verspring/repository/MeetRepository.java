package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.meets.Meet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, Long> {
    Page<Meet> findAllByUserId(Long userId, Pageable pageable);
}
