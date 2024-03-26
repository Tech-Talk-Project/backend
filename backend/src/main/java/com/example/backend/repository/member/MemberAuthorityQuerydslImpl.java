package com.example.backend.repository.member;

import com.example.backend.entity.member.Authority;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static com.example.backend.entity.member.QMemberAuthority.*;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAuthorityQuerydslImpl implements MemberAuthorityQuerydsl{
    private final JPAQueryFactory query;

    public List<Authority> findAuthoritiesByMemberId(Long memberId) {
        return query
                .select(memberAuthority.authority)
                .from(memberAuthority)
                .where(memberAuthority.member.id.eq(memberId))
                .fetch();
    }

    public void deleteAuthoritiesByMemberId(Long memberId) {
        query
            .delete(memberAuthority)
            .where(memberAuthority.member.id.eq(memberId))
            .execute();
    }
}
