package com.example.backend.controller.dto.response;

import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.Profile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProfilePaginationResponseDto {
    private List<ProfileData> data;

    private String nextCursor;
    private int count;

    @Getter @Setter
    private static class ProfileData {
        private Long memberId;
        private String name;
        private String job;
        private String introduction;
        private String imageUrl;
        private List<String> skills = new ArrayList<>();
    }

    public ProfilePaginationResponseDto(List<Member> members) {
        this.data = new ArrayList<>();
        if (members.isEmpty()) {
            this.nextCursor = "";
            this.count = 0;
            return;
        }
        for (Member member : members) {
            ProfileData profileData = new ProfileData();
            profileData.setMemberId(member.getId());
            profileData.setName(member.getName());
            profileData.setJob(member.getProfile().getJob());
            profileData.setIntroduction(member.getProfile().getIntroduction());
            profileData.setImageUrl(member.getImageUrl());
            member.getProfile().getProfileSkills().forEach(profileSkill -> {
                profileData.getSkills().add(profileSkill.getSkill().getName());
            });
            data.add(profileData);
        }
        this.count = members.size();
        this.nextCursor = members.get(members.size() - 1).getUpdatedAt().toString();
    }

    public ProfilePaginationResponseDto(List<Member> members, String nextCursor) {
        this.data = new ArrayList<>();
        if (members.isEmpty()) {
            this.nextCursor = "";
            this.count = 0;
            return;
        }
        for (Member member : members) {
            ProfileData profileData = new ProfileData();
            profileData.setMemberId(member.getId());
            profileData.setName(member.getName());
            profileData.setJob(member.getProfile().getJob());
            profileData.setIntroduction(member.getProfile().getIntroduction());
            profileData.setImageUrl(member.getImageUrl());
            member.getProfile().getProfileSkills().forEach(profileSkill -> {
                profileData.getSkills().add(profileSkill.getSkill().getName());
            });
            data.add(profileData);
        }
        this.count = members.size();
        this.nextCursor = nextCursor;
    }
}
