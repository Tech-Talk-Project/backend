package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.ProfileDto;
import com.example.backend.entity.member.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileOffsetPaginationResponseDto {
    List<ProfileDto> data = new ArrayList<>();
    int totalCount;
    int totalPage;

    public ProfileOffsetPaginationResponseDto(List<Member> members, int totalCount, int totalPage) {
        for (Member member : members) {
            ProfileDto profileData = ProfileDto.builder()
                    .memberId(member.getId())
                    .name(member.getName())
                    .job(member.getProfile().getJob())
                    .introduction(member.getProfile().getIntroduction())
                    .imageUrl(member.getImageUrl())
                    .skills(member.getProfile().getSkills())
                    .build();
            data.add(profileData);
        }
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }
}
