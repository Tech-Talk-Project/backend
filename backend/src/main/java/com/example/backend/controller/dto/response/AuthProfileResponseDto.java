package com.example.backend.controller.dto.response;


import com.example.backend.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthProfileResponseDto extends ProfileResponseDto{
    private boolean following;

    public AuthProfileResponseDto(Member member, boolean following) {
        super(member);
        this.following = following;
    }
}
