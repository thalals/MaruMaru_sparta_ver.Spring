package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.articles.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
} //Jpa 테이블 상속