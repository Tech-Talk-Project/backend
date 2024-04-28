package com.example.backend.controller;

import com.example.backend.board.dto.request.BoardCreateRequestDto;
import com.example.backend.board.service.BoardCategory;
import com.example.backend.board.service.boardManage.FreeBoardManageService;
import com.example.backend.board.service.boardManage.ProjectBoardManageService;
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
    private final ProjectBoardManageService projectBoardManageService;
    private final FreeBoardManageService freeBoardManageService;
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

    @GetMapping("/board-test")
    public String boardTest(@RequestParam Long memberId) {
        for (int i = 0; i < 105; i++) {
            BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(BoardCategory.PROJECT, "test" + i);
            projectBoardManageService.create(memberId, boardCreateRequestDto);
        }

        for (int i = 0; i < 105; i++) {
            BoardCreateRequestDto boardCreateRequestDto = new BoardCreateRequestDto(BoardCategory.FREE, "test" + i);
            freeBoardManageService.create(memberId, boardCreateRequestDto);
        }
        return "board-test";
    }
}
