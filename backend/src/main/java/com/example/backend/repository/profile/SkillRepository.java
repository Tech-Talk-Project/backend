package com.example.backend.repository.profile;

import com.example.backend.entity.profile.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
