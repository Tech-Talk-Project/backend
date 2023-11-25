package com.example.backend.auth.dto;

import com.example.backend.entity.member.Member;
import com.example.backend.security.dto.AtRtDto;
import lombok.Data;

@Data
public class LoginSuccessDto {
    private Member member;
    private AtRtDto atRtDto;
    private boolean firstLogin;

    public LoginSuccessDto(Member member, AtRtDto atRtDto, boolean firstLogin) {
        this.member = member;
        this.atRtDto = atRtDto;
        this.firstLogin = firstLogin;
    }
}
