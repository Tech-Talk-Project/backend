package com.example.backend.service.member;

import com.example.backend.controller.dto.response.MemberSearchResponseDto;
import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSearchService {
    private final MemberRepository memberRepository;

    public MemberSearchResponseDto findByEmailStartsWithLimit(String email, int limit) {
        List<Member> members = memberRepository.findByEmailStartsWithLimit(email, limit);
        return new MemberSearchResponseDto(members);
    }
}
