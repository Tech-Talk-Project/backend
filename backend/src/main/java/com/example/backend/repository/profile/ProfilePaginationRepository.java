package com.example.backend.repository.profile;

import com.example.backend.entity.profile.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.backend.entity.profile.QProfile.*;
import static com.example.backend.entity.profile.QProfileSkill.profileSkill;
import static com.example.backend.entity.profile.QSkill.*;

@Transactional(readOnly = true)
@Repository
@RequiredArgsConstructor
public class ProfilePaginationRepository {
    private final JPAQueryFactory query;

    public List<Profile> pagingAfterCursor(
            LocalDateTime cursor, int limit, List<ESkill> eSkills) {


        // eSKills 를 모두 포함하는 profile 을 찾는다.
        List<Long> subQuery = query.select(profile.id)
                .from(profile)
                .innerJoin(profile.profileSkills, profileSkill)
                .innerJoin(profileSkill.skill, skill)
                .where(skill.eSkill.in(eSkills))
                .groupBy(profile.id)
                .having(profile.count().goe((long) eSkills.size()))
                .fetch();
        System.out.println(subQuery);

        // fetchJoin 을 이용해 profileSkill, skill 을 한번에 가져온다.
        // eSkills 를 모두 포함하고 있는 profile 중 커서 기반 페이지네이션을 이용해 limit 만큼 가져온다.
        return query
                .selectFrom(profile)
                .innerJoin(profile.profileSkills, profileSkill).fetchJoin()
                .innerJoin(profileSkill.skill, skill).fetchJoin()
                .where(
                        profile.id.in(subQuery),
                        profile.updatedAt.lt(cursor)
                )
                .orderBy(profile.updatedAt.desc())
                .limit(limit)
                .fetch();
    }
}
