package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Profile 과 Category 의 다대다 관계를 연결하는 중간 테이블
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileSkill {
    @Id @GeneratedValue
    @Column(name = "profile_skill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public ProfileSkill(Skill skill, Profile profile) {
        this.skill = skill;
        this.profile = profile;
    }
}
