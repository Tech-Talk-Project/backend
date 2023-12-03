package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.InfoDto;
import com.example.backend.entity.profile.Link;
import com.example.backend.entity.profile.Profile;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProfileViewResponseDto {

    private InfoDto info;

    private String imageUrl;

    private String introduction;

    private List<String> links;

    private Set<String> skills;

    private String detailedDescription;

    public ProfileViewResponseDto(Profile profile) {
        this.info = new InfoDto(profile.getInfo().getNickname(), profile.getInfo().getJob(), profile.getInfo().getEmail());
        this.imageUrl = profile.getImageUrl();
        this.introduction = profile.getIntroduction();
        this.detailedDescription = profile.getDetailedDescription();
        this.skills = profile.getProfileSkills().stream().map(
                profileSkill -> profileSkill.getSkill().getESkill().getName()
        ).collect(Collectors.toSet());
        this.links = profile.getLinks().stream().map(Link::getUrl).collect(Collectors.toList());
    }
}
