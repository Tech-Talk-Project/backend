package com.example.backend.repository.member;

import com.example.backend.entity.member.Member;

import java.util.List;

public interface MemberQuerydsl {
    List<Member> findByEmailStartsWithLimit(String email, int limit);

    List<Member> findByEmailContainsLimit(String email, int limit);

    String findNameById(Long memberId);

    List<Member> findByIdIn(List<Long> memberIds);
}
