package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;

public class SelectedProfileResponseDto extends MyProfileResponseDto {

    public SelectedProfileResponseDto(Member member) {
        super(member);
    }
}
