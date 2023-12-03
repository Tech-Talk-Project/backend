package com.example.backend.repository.profile;

import com.example.backend.entity.profile.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.entity.member.QMember.*;
import static com.example.backend.entity.profile.QLink.*;
import static com.example.backend.entity.profile.QProfile.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileRepository {
    private final JPAQueryFactory query;
    public Profile findProfileWithAll(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .leftJoin(profile.links, link).fetchJoin()
                .leftJoin(profile.profileSkills, QProfileSkill.profileSkill).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne()
                .getProfile();
    }

    public Profile findProfileWithInfo(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne()
                .getProfile();
    }

    public Profile findProfileWithLinks(Long memberId) {
        return query
                .selectFrom(member)
                .innerJoin(member.profile, profile).fetchJoin()
                .leftJoin(profile.links, link).fetchJoin()
                .where(member.id.eq(memberId))
                .fetchOne()
                .getProfile();
    }

    @Transactional
    public void updateProfileInfo(Long memberId, String nickname, String job) {
        Long profileId = getProfileIdByMemberID(memberId);
        query
                .update(profile)
                .set(profile.info.nickname, nickname)
                .set(profile.info.job, job)
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
