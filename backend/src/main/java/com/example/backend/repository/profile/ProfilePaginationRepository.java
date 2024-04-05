package com.example.backend.repository.profile;

import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.backend.entity.member.QMember.member;
import static com.example.backend.entity.profile.QProfile.*;
import static com.example.backend.entity.profile.QProfileSkill.profileSkill;
import static com.example.backend.entity.profile.QSkill.*;

@Transactional(readOnly = true)
@Repository
@RequiredArgsConstructor
public class ProfilePaginationRepository {
    private final JPAQueryFactory query;

    public List<Member> pagingBySkillsAfterCursor(
            LocalDateTime cursor, int limit, List<ESkill> eSkills) {
        if (eSkills.isEmpty()) {
            return pagingAfterCursor(cursor, limit);
        }

        // eSKills 를 모두 포함하는 profile id 를 찾는다.
        JPAQuery<Long> subQuery = query.select(profile.id)
                .from(profile)
                .innerJoin(profile.profileSkills, profileSkill)
                .innerJoin(profileSkill.skill, skill)
                .where(skill.eSkill.in(eSkills))
                .groupBy(profile.id)
                .having(profile.count().goe((long) eSkills.size()));

        // fetchJoin 을 이용해 profileSkill, skill 을 한번에 가져온다.
        // eSkills 를 모두 포함하고 있는 profile 중 커서 기반 페이지네이션을 이용해 limit 만큼 가져온다.
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .where(
                        profile.id.in(subQuery),
                        profile.updatedAt.lt(cursor)
                )
                .orderBy(profile.updatedAt.desc())
                .limit(limit)
                .fetch();
    }

    public List<Member> pagingAfterCursor(LocalDateTime cursor, int limit) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .where(profile.updatedAt.lt(cursor))
                .orderBy(profile.updatedAt.desc())
                .limit(limit)
                .fetch();
    }
}
