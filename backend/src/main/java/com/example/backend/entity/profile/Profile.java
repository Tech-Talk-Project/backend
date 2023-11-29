package com.example.backend.entity.profile;

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

    private String nickname;

    private String job;

    private String email;

    private String introduction;

    private String imageUrl;

    @Lob
    private String detailedDescription;

    @OneToMany(mappedBy = "profile")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private Set<ProfileSkill> profileCategories = new HashSet<>();

    public Profile(String nickname, String email, String imageUrl) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
