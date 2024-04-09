package com.example.backend.controller;

import com.example.backend.entity.member.Member;
import com.example.backend.repository.member.MemberRepository;
import com.example.backend.service.follow.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final MemberRepository memberRepository;
    private final FollowService followService;

    @GetMapping("/user")
    public Long user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = (Long) principal;
        return memberId;
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/follow-test")
    public String followTest(@RequestParam Long memberId) {
        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            if (memberId.equals(member.getId())) continue;
            followService.addFollowing(memberId, member.getId());
        }
        return "follow-test";
    }
}
