package com.example.marumaru_sparta_verspring.repository;

import com.example.marumaru_sparta_verspring.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Override
    @Query("SELECT p FROM Profile p join fetch p.user")
    List<Profile> findAll();
    List<Profile> findByUserId(Long userId);
} //Jpa 테이블 상속