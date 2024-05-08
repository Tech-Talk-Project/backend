package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.AuthProfileResponseDto;
import com.example.backend.controller.dto.response.ProfileResponseDto;
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

    public ProfileResponseDto getProfile(Long memberId) {
        Member member = memberProfileRepository.findByIdWithProfile(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + memberId)
        );
        return new ProfileResponseDto(member);
    }

    public ProfileResponseDto getSelectedProfile(Long selectedMemberId) {
        Member member = memberProfileRepository.findByIdWithProfile(selectedMemberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + selectedMemberId)
        );
        return new ProfileResponseDto(member);
    }

    public AuthProfileResponseDto getSelectedProfileWhenLogin(Long memberId, Long selectedMemberId) {
        Member member = memberProfileRepository.findByIdWithProfile(selectedMemberId).orElseThrow(
                () -> new IllegalArgumentException("Member not found with memberId: " + selectedMemberId)
        );
        boolean isFollowing = followService.isFollowing(memberId, selectedMemberId);
        return new AuthProfileResponseDto(member, isFollowing);
    }
}
