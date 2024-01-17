package com.example.backend.service.profile.dto;

import com.example.backend.entity.member.Member;
import lombok.Data;

@Data
public class SimpleMemberProfileDto {
    private Long memberId;
    private String nickname;
    private String imageUrl;

    public SimpleMemberProfileDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getProfile().getInfo().getNickname();
        this.imageUrl = member.getProfile().getImageUrl();
    }
}
