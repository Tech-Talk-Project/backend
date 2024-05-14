package com.example.backend.repository.member;

import com.example.backend.entity.member.Authority;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.entity.member.QAuthority.authority;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityRepository {
    private final JPAQueryFactory query;

    public List<Authority> findAllByMemberId(Long memberId) {
        return query
                .select(authority)
                .from(authority)
                .where(authority.member.id.eq(memberId))
                .fetch();
    }


}
