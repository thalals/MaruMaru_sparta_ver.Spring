package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByUserId(Long userId);
} //Jpa 테이블 상속