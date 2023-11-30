package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.UpdateInfoRequestDto;
import com.example.backend.entity.profile.Profile;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileUpdateService {
    private final MemberProfileRepository memberProfileRepository;

    public void updateInfo(Long memberId, UpdateInfoRequestDto updateInfoRequestDto) {
        String nickname = updateInfoRequestDto.getName();
        String job =  updateInfoRequestDto.getJob();
        memberProfileRepository.updateProfileInfo(memberId, nickname, job);
//        Profile profileWithInfo = memberProfileRepository.findProfileWithInfo(memberId);
//        profileWithInfo.updateInfo(updateInfoRequestDto);
    }
}
