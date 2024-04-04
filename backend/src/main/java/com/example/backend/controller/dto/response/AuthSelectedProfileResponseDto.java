package com.example.backend.controller.dto.response;


import com.example.backend.entity.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthSelectedProfileResponseDto extends SelectedProfileResponseDto{
    private boolean following;

    public AuthSelectedProfileResponseDto(Member member, boolean following) {
        super(member);
        this.following = following;
    }
}
