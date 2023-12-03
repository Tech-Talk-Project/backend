package com.example.backend.entity.profile;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Skill {
    @Id @GeneratedValue
    @Column(name = "skill_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ESkill eSkill;

    public String getName() {
        return eSkill.getName();
    }

    public Skill(ESkill eSkill) {
        this.eSkill = eSkill;
    }
}
