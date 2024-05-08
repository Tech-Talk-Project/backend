package com.example.backend.entity.profile;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
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
public class Profile extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    private String job;

    private String introduction;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailedDescription;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();

    public void updateLinks(List<String> links) {
        this.links.clear();
        for (String link : links) {
            addLink(link);
        }
    }

    private void addLink(String link) {
        this.links.add(new Link(link, this));
    }

    public void updateProfileSkills(List<ESkill> eSkills) {
        this.skills.clear();
        for (ESkill eskill : eSkills) {
            addSkill(eskill);
        }
    }

    private void addSkill(ESkill eSkill) {
        this.skills.add(new Skill(eSkill, this));
    }
}
