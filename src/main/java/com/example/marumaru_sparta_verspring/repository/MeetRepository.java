package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, Long> {
}
