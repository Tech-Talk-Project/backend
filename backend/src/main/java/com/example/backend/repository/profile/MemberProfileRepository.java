package com.example.backend.repository.profile;

import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.backend.entity.member.QMember.*;
import static com.example.backend.entity.profile.QLink.*;
import static com.example.backend.entity.profile.QProfile.*;
import static com.example.backend.entity.profile.QProfileSkill.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileRepository {
    private final JPAQueryFactory query;
    public Member findByIdWithProfileWithAll(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .leftJoin(profile.links, link).fetchJoin()
                .leftJoin(profile.profileSkills, profileSkill).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    public Member findByIdWithProfileInfo(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    public Member findByIdWithProfileWithLinks(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .leftJoin(profile.links, link).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    public Member findByIdWithProfileWithSkills(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .leftJoin(profile.profileSkills, profileSkill).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    @Transactional
    public void updateProfileInfo(Long memberId, String name, String job) {
        Long profileId = getProfileIdByMemberID(memberId);
        query
                .update(member)
                .set(member.name, name)
                .where(member.id.eq(memberId))
                .execute();
        query
                .update(profile)
                .set(profile.job, job)
                .where(profile.id.eq(profileId))
                .execute();
    }

    @Transactional
    public void updateProfileIntroduction(Long memberId, String introduction) {
        Long profileId = getProfileIdByMemberID(memberId);
        query
                .update(profile)
                .set(profile.introduction, introduction)
                .where(profile.id.eq(profileId))
                .execute();
    }

    private Long getProfileIdByMemberID(Long memberId) {
        return query
                .select(member.profile.id)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    public void updateProfileDescription(Long memberId, String detailedDescription) {
        Long profileId = getProfileIdByMemberID(memberId);
        query
                .update(profile)
                .set(profile.detailedDescription, detailedDescription)
                .where(profile.id.eq(profileId))
                .execute();
    }


}
