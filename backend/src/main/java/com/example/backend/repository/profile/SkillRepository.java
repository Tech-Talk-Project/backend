package com.example.backend.repository.profile;

import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Skill;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select s from Skill s where s.eSkill = :eSkill")
    Optional<Skill> findByESkill(@Param("eSkill") ESkill eSkill);
}
