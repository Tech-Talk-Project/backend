package com.example.backend.entity.profile;

import com.example.backend.controller.dto.request.UpdateInfoRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;
    
    @Embedded
    private Info info;

    private String introduction;

    private String imageUrl;

    @Lob
    private String detailedDescription;

    @OneToMany(mappedBy = "profile")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private Set<ProfileSkill> profileSkills = new HashSet<>();

    public Profile(String nickname, String email, String imageUrl) {
        this.info = Info.builder()
                .nickname(nickname)
                .email(email)
                .build();
        this.imageUrl = imageUrl;
    }

    public void updateInfo(UpdateInfoRequestDto updateInfoRequestDto) {
        String nickname = updateInfoRequestDto.getName();
        String job =  updateInfoRequestDto.getJob();
        this.info.updateInfo(nickname, job);
    }
}
