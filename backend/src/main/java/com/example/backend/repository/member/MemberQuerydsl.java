package com.example.backend.repository.member;

import com.example.backend.entity.member.Member;
import com.example.backend.entity.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.entity.member.QMember.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQuerydsl {
    private final JPAQueryFactory query;

    public List<Member> findByEmailStartsWithLimit(String email, int limit) {
        return query
                .selectFrom(member)
                .where(member.email.startsWith(email))
                .limit(limit)
                .fetch();
    }

    public List<Member> findByEmailContainsLimit(String email, int limit) {
        return query
                .selectFrom(member)
                .where(member.email.contains(email))
                .limit(limit)
                .fetch();
    }

    public String findNameById(Long memberId) {
        return query
                .select(member.name)
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    public boolean existsByMemberId(Long memberId) {
        return query
                .selectOne()
                .from(member)
                .where(member.id.eq(memberId))
                .fetchFirst() != null;
    }
}
