package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Profile {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    private String job;

    private String email;

    private String introduction;

    @Lob
    private String detailedDescription;

    @OneToMany(mappedBy = "profile")
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private Set<ProfileSkill> profileCategories;
}
