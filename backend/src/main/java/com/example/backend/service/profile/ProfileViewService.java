package com.example.backend.service.profile;

import com.example.backend.controller.dto.response.AuthSelectedProfileResponseDto;
import com.example.backend.controller.dto.response.MyProfileResponseDto;
import com.example.backend.controller.dto.response.SelectedProfileResponseDto;
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

    public MyProfileResponseDto getProfile(Long memberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(memberId);
        return new MyProfileResponseDto(member);
    }

    public SelectedProfileResponseDto getSelectedProfile(Long selectedMemberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(selectedMemberId);
        return new SelectedProfileResponseDto(member);
    }

    public AuthSelectedProfileResponseDto getSelectedProfileWhenLogin(Long memberId, Long selectedMemberId) {
        Member member = memberProfileRepository.findByIdWithProfileWithAll(selectedMemberId);
        boolean isFollowing = followService.isFollowing(memberId, selectedMemberId);
        return new AuthSelectedProfileResponseDto(member, isFollowing);
    }
}
