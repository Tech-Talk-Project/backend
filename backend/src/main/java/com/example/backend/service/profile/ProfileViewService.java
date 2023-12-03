package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.ProfileViewResponseDto;
import com.example.backend.entity.dto.ProfileWithAllDto;
import com.example.backend.entity.profile.Profile;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
    private final MemberProfileRepository memberProfileRepository;

    public ProfileViewResponseDto gerProfile(Long memberId) {
        ProfileWithAllDto profileWithAll = memberProfileRepository.findProfileWithAll(memberId);
        return new ProfileViewResponseDto(profileWithAll);
    }
}
