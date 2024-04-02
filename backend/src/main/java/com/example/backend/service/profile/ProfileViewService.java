package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.MyProfileViewResponseDto;
import com.example.backend.controller.dto.response.ProfileViewResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.profile.MemberProfileRepository;
import com.example.backend.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileViewService {
    private final MemberProfileRepository memberProfileRepository;
    private final FollowService followService;

    public MyProfileViewResponseDto gerMyProfile(Long memberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(memberId);
        return new MyProfileViewResponseDto(member);
    }

    public ProfileViewResponseDto getProfile(Long memberId, Long selectedMemberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(selectedMemberId);
        boolean isFollowing = followService.isFollowing(memberId, selectedMemberId);
        return new ProfileViewResponseDto(member, isFollowing);
    }
}
