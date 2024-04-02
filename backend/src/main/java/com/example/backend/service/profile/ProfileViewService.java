package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.MyProfileViewResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
    private final MemberProfileRepository memberProfileRepository;

    public MyProfileViewResponseDto gerMyProfile(Long memberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(memberId);
        return new MyProfileViewResponseDto(member);
    }
}
