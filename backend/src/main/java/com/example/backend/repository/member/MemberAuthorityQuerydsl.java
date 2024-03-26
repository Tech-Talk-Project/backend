package com.example.backend.repository.member;

import com.example.backend.entity.member.Authority;

import java.util.List;

public interface MemberAuthorityQuerydsl {
    List<Authority> findAuthoritiesByMemberId(Long memberId);
    void deleteAuthoritiesByMemberId(Long memberId);
}
