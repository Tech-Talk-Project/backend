package com.example.backend.controller;

import com.example.backend.controller.dto.response.MemberSearchResponseDto;
import com.example.backend.service.member.MemberSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberSearchController {
    private final MemberSearchService memberSearchService;

    @GetMapping("/members/search")
    public ResponseEntity<MemberSearchResponseDto> searchMembers(
            @RequestParam String email,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        log.info("/members/search called");
        return ResponseEntity.ok(memberSearchService.findByEmailStartsWithLimit(email, limit));
    }
}
