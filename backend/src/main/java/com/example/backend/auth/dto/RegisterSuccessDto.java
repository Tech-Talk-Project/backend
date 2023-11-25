package com.example.backend.auth.dto;

import com.example.backend.entity.member.Member;
import lombok.Data;

@Data
public class RegisterSuccessDto {
    private Member member;
    private boolean firstLogin;

    public RegisterSuccessDto(Member member, boolean firstLogin) {
        this.member = member;
        this.firstLogin = firstLogin;
    }
}
