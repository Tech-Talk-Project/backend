package com.example.backend.controller.dto.response;


import com.example.backend.entity.member.Member;

public class AuthSelectedProfileResponseDto extends SelectedProfileResponseDto{
    private boolean isFollowing;

    public AuthSelectedProfileResponseDto(Member member, boolean isFollowing) {
        super(member);
        this.isFollowing = isFollowing;
    }
}
