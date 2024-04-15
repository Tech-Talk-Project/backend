package com.example.backend.repository.profile;

import com.example.backend.entity.profile.ESkill;
import com.example.backend.entity.profile.Skill;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.backend.entity.profile.QSkill.skill;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillRepository  {
    private final JPAQueryFactory query;
    private final EntityManager em;


    public Skill findByESkill(ESkill eSkill) {
        return query
                .selectFrom(skill)
                .where(skill.name.eq(eSkill.getName()))
                .fetchOne();
    }

    public Skill findByName(String name) {
        return query
                .selectFrom(skill)
                .where(skill.name.eq(name))
                .fetchOne();
    }

    public List<Skill> findAllByName(List<String> names) {
        return query
                .selectFrom(skill)
                .where(skill.name.in(names))
                .fetch();
    }

    @Transactional
    public void save(Skill skill) {
        em.persist(skill);
        em.flush();
    }
}
