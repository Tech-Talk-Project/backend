package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.ProfileViewResponseDto;
import com.example.backend.entity.dto.ProfileWithAllDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.Profile;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
    private final MemberProfileRepository memberProfileRepository;

    public ProfileViewResponseDto gerProfile(Long memberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(memberId);
        return new ProfileViewResponseDto(member);
    }
}
