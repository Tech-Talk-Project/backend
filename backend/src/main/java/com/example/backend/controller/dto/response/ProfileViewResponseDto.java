package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;

public class ProfileViewResponseDto extends MyProfileViewResponseDto{
    private boolean isFollowing;

    public ProfileViewResponseDto(Member member, boolean isFollowing) {
        super(member);
        this.isFollowing = isFollowing;
    }
}
