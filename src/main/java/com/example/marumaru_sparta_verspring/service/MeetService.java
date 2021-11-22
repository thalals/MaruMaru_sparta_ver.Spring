package com.example.marumaru_sparta_verspring.service;

import com.example.marumaru_sparta_verspring.domain.Meet;
import com.example.marumaru_sparta_verspring.dto.MeetRequestDto;
import com.example.marumaru_sparta_verspring.repository.MeetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetService {

    private final MeetRepository meetRepository;

    public Meet saveMeet(MeetRequestDto meetRequestDto) {
        Meet meet = new Meet();
        meet.setTitle(meetRequestDto.getTitle());
        meet.setContent(meetRequestDto.getContent());
        meetRepository.save(meet);
        return meet;
    }

}
