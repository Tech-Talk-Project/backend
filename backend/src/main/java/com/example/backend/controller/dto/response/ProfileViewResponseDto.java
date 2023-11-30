package com.example.backend.controller.dto.response;

import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Link;
import com.example.backend.entity.profile.Profile;
import com.example.backend.entity.profile.ProfileSkill;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProfileViewResponseDto {

    private Info info;

    private String introduction;

    private List<String> links;

    private Set<ESkill> skills;

    private String detailedDescription;

    @Data
    static class Info {
        private String name;
        private String job;
        private String email;

        public Info(String name, String job, String email) {
            this.name = name;
            this.job = job;
            this.email = email;
        }
    }

    public ProfileViewResponseDto(Profile profile) {
        this.info = new Info(profile.getInfo().getNickname(), profile.getInfo().getJob(), profile.getInfo().getEmail());
        this.introduction = profile.getIntroduction();
        this.detailedDescription = profile.getDetailedDescription();
        this.skills = profile.getProfileSkills().stream().map(
                profileSkill -> profileSkill.getSkill().getESkill()
        ).collect(Collectors.toSet());
        this.links = profile.getLinks().stream().map(Link::getUrl).collect(Collectors.toList());
    }
}
