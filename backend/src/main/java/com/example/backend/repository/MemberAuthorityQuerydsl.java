package com.example.backend.repository;

import com.example.backend.entity.member.Authority;

import java.util.List;

public interface MemberAuthorityQuerydsl {
    List<Authority> findAuthoritiesByMemberId(Long memberId);
}
