package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Profile 과 Category 의 다대다 관계를 연결하는 중간 테이블
 */
@Entity
@Getter
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
}
