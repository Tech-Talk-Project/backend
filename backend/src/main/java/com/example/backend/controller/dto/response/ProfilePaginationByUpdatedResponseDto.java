package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfilePaginationByUpdatedResponseDto extends ProfilePaginationResponseDto{
    private String nextCursor;

    public ProfilePaginationByUpdatedResponseDto(List<Member> members) {
        if (members.isEmpty()) {
            this.nextCursor = "";
            this.count = 0;
            return;
        }
        for (Member member : members) {
            ProfileData profileData = ProfileData.builder()
                            .memberId(member.getId())
                            .name(member.getName())
                            .job(member.getProfile().getJob())
                            .introduction(member.getProfile().getIntroduction())
                            .imageUrl(member.getImageUrl())
                            .skills(member.getProfile().getProfileSkills())
                            .build();
            data.add(profileData);
        }
        this.count = members.size();
        this.nextCursor = members.get(members.size() - 1).getUpdatedAt().toString();
    }
}
