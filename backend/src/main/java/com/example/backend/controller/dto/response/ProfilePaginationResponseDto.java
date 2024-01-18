package com.example.backend.controller.dto.response;

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

    public ProfilePaginationResponseDto(List<Profile> profiles) {
        this.data = new ArrayList<>();
        if (profiles.isEmpty()) {
            this.nextCursor = "";
            this.count = 0;
            return;
        }
        for (Profile profile : profiles) {
            ProfileData profileData = new ProfileData();
            profileData.setMemberId(profile.getMember().getId());
            profileData.setName(profile.getInfo().getNickname());
            profileData.setJob(profile.getInfo().getJob());
            profileData.setIntroduction(profile.getIntroduction());
            profileData.setImageUrl(profile.getImageUrl());
            profile.getProfileSkills().forEach(profileSkill -> {
                profileData.getSkills().add(profileSkill.getSkill().getName());
            });
            data.add(profileData);
        }
        this.count = profiles.size();
        this.nextCursor = profiles.get(profiles.size() - 1).getUpdatedAt().toString();
    }
}
