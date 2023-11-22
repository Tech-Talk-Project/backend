package com.example.backend.repository;

import com.example.backend.entity.member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.backend.entity.member.QMember.*;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthorityRepository {
    private final JPAQueryFactory query;

    public Optional<Member> findByEmailWithAuthorities(String email) {
        return Optional.ofNullable(
                query
                        .selectFrom(member)
                        .join(member.authorities)
                        .fetchJoin()
                        .where(member.email.eq(email))
                        .fetchFirst()
        );
    }

    public Optional<Member> findByIdWithAuthorities(Long id) {
        return Optional.ofNullable(
                query
                        .selectFrom(member)
                        .join(member.authorities)
                        .fetchJoin()
                        .where(member.id.eq(id))
                        .fetchFirst()
        );
    }
}
